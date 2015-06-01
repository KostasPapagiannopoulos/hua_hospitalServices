from django.http import HttpResponse
from django.shortcuts import render
from suds.client import Client
from django.contrib.auth.forms import UserCreationForm
from django.http import HttpResponseRedirect
from django.core.context_processors import csrf
import urllib2, base64, json
from forms import MyRegistrationForm
from forms import USER_TYPE_CHOICES
from django.contrib.auth.decorators import login_required
from django.contrib import auth

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
    request = urllib2.Request("http://62.217.127.56/phprest/users/employees")
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
    return render(request,'login.html', c)


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
    return render(request,'loggedin.html',
                              {'full_name': request.user.username, 'auth': request.user.is_authenticated})


def invalid_login(request):
    return render(request,'invalid_login.html')


def logout(request):
    auth.logout(request)
    return render(request,'logout.html')


def register_user(request):
    if request.method == 'POST':
        form = MyRegistrationForm(request.POST)
        if form.is_valid():
            if not(form.cleaned_data['user_type'] == 'Patient'):
                # if its a patient, we dont need to check his username
                response = java_insertUser(form)
            else:
                if not (userExists(form.cleaned_data['username'])):
                    # if the user does not exist
                    return HttpResponseRedirect('/accounts/register_failed')
                # else save him
                response = java_insertStaff(form)

            if (response):
                form.save()
                return HttpResponseRedirect('/accounts/register_success')
            else:
                 return HttpResponseRedirect('/accounts/register_failed')
    else:
        form = MyRegistrationForm()
    args = {}
    args.update(csrf(request))

    args['form'] = form

    return render(request,'register.html', args)


java_Patient_Clinet = Client('http://localhost:8080/hospitalServices/PatientMethodsService?WSDL')
def java_insertUser(form):
    patient = java_Patient_Clinet.factory.create('patient')
    patient.patientName = form.cleaned_data['first_name']
    patient.patientSurname = form.cleaned_data['last_name']
    patient.patientGender = form.cleaned_data['gender']
    patient.insuranceFund = 'insuranceFund'
    patient.AMKA = form.cleaned_data['amka']
    patient.bloodType = 'bloodType'
    patient.address = 'address'
    patient.country = 'country'
    patient.email = form.cleaned_data['email']
    return java_Patient_Clinet.service.insertPatient(patient) != "Error"


java_Staff_Client = Client('http://localhost:8080/hospitalServices/HospitalStaffMethodsService?WSDL')
def java_insertStaff(form):

    hospitalstaff = java_Staff_Client.factory.create('hospitalStaff')

    hospitalstaff.firstName = form.cleaned_data['first_name']
    hospitalstaff.lastSurname = form.cleaned_data['last_name']
    hospitalstaff.gender = form.cleaned_data['gender']
    hospitalstaff.birthDate = '2015-05-01'
    hospitalstaff.staffType = form.cleaned_data['user_type']
    hospitalstaff.emp_no = form.cleaned_data['username']

    return java_Staff_Client.service.insertStaff(hospitalstaff) != "Error"



def register_success(request):
    return render(request,'register_success.html')


def register_failed(request):
    return render(request,'register_failed.html')


soap_client_PatientServices = Client('http://localhost:8080/hospitalServices/PatientMethodsService?WSDL')

@login_required
def showpatients (request):

   results = soap_client_PatientServices.service.returnAllPatients()
   context = { 'results':results, }
   return render(request, 'allpatients.html', context)


soap_client_ClinicServices = Client('http://localhost:8080/hospitalServices/ClinicMethodsService?WSDL')

def clinics (request):
   results = soap_client_ClinicServices.service.returnAllClinics()
   context = { 'results':results, }
   return render(request, 'allclinics.html', context)


def doctors (request):
   results = soap_client_ClinicServices.service.returnAllDoctors()
   context = { 'results':results, }
   return render(request, 'alldoctors.html', context)


@login_required
def duty (request, doctorid):
   results = soap_client_ClinicServices.service.returnAllClinicDuty(doctorid)
   context = { 'results':results, }
   return render(request, 'allduty.html', context)
