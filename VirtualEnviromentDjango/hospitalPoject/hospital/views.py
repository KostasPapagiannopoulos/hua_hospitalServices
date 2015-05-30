from django.http import HttpResponse
from django.shortcuts import render
from suds.client import Client
from django.shortcuts import render_to_response
from django.http import HttpResponseRedirect
from django.contrib import auth
from django.core.context_processors import csrf
from django.contrib.auth.forms import UserCreationForm
import urllib2, base64, json
from forms import MyRegistrationForm


def index(request):
    return HttpResponse("Rango says Rango")


# soap_client = Client('http://10.100.51.100:8080/lab3/UsersWS?WSDL')
def showusers(request):
    request = urllib2.Request("http://62.217.127.56/phprest/index.php/users/users")
    username = 'admin'
    password = '1234'
    base64string = base64.encodestring('%s:%s' % (username, password)).replace('\n', '')
    request.add_header("Authorization", "Basic %s" % base64string)
    result = urllib2.urlopen(request)
    # print result.read()
    data = json.load(result)
    return HttpResponse(data)


def userExists(emp_no):
    # Get the data from the endpoint
    request = urllib2.Request("http://62.217.127.56/phprest/index.php/users/users")
    username = 'admin'
    password = '1234'
    base64string = base64.encodestring('%s:%s' % (username, password)).replace('\n', '')
    request.add_header("Authorization", "Basic %s" % base64string)
    result = urllib2.urlopen(request)
    data = json.load(result)
    # check in the list it there is any dictionary with the appropriate username
    return next((x for x in data if x['emp_no'] == emp_no), None) is not None


def login(request):
    c = {}
    c.update(csrf(request))
    return render_to_response('login.html', c)


def auth_view(request):
    username = request.POST.get('username', '')
    password = request.POST.get('password', '')
    user = auth.authenticate(username=username, password=password)

    if user is not None:
        auth.login(request, user)
        return HttpResponseRedirect('/accounts/loggedin')
    else:
        return HttpResponseRedirect('/accounts/invalid')


def loggedin(request):
    return render_to_response('loggedin.html',
                              {'full_name': request.user.username})


def invalid_login(request):
    return render_to_response('invalid_login.html')


def logout(request):
    auth.logout(request)
    return render_to_response('logout.html')


def register_user(request):
    if request.method == 'POST':
        form = UserCreationForm(request.POST)
        if form.is_valid():
            if not (userExists(form.cleaned_data['username'])):
                # if the user does not exist
                return HttpResponseRedirect('/accounts/register_failed')

            # else save him
            java_insertStaff(form.cleaned_data['username'])
            form.save()
            return HttpResponseRedirect('/accounts/register_success')

    else:
        form = UserCreationForm()
    args = {}
    args.update(csrf(request))

    args['form'] = form

    return render_to_response('register.html', args)


java_Patient_Clinet = Client('http://localhost:8080/hospitalServices/PatientService?WSDL')


def java_insertUser(patientName):
    java_Patient_Clinet.service.setPatientID(1);
    java_Patient_Clinet.service.setPatientName(patientName)
    java_Patient_Clinet.service.setPatientSurname("patientSurname")
    java_Patient_Clinet.service.setPatientGender("patientGender")
    java_Patient_Clinet.service.setInsuranceFund("insuranceFund")
    java_Patient_Clinet.service.setAMKA(000)
    java_Patient_Clinet.service.setBloodType("bloodType")
    java_Patient_Clinet.service.setAddress("address")
    java_Patient_Clinet.service.setCountry("country")
    java_Patient_Clinet.service.insertPatient()
    return HttpResponse("Rango says Rango")


java_Staff_Client = Client('http://localhost:8080/hospitalServices/HospitalStaffService?WSDL')


def java_insertStaff(username):
    print java_Staff_Client
    hospitalstaff = java_Staff_Client.factory.create('hospitalStaff')
    hospitalstaff.staffID = 100
    hospitalstaff.firstName = 'name_of_' + username
    hospitalstaff.lastSurname = 'surname_of_' + username
    hospitalstaff.gender = 'M'
    hospitalstaff.birthDate = '2015-05-01'
    hospitalstaff.staffType = 1
    hospitalstaff.emp_no = username

    response = java_Staff_Client.service.insertStaff(hospitalstaff)
    return HttpResponse("Rango says " + response)


def register_success(request):
    return render_to_response('register_success.html')


def register_failed(request):
    return render_to_response('register_failed.html')
