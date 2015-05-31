
from django.http import HttpResponse
from django.shortcuts import render
from suds.client import Client
from django.shortcuts import render_to_response
from django.http import HttpResponseRedirect
from django.contrib import auth
from django.core.context_processors import csrf
from django.contrib.auth.forms import UserCreationForm
import urllib2, base64, json
from forms import PatientnForm
from django.contrib.auth.decorators import login_required



java_Patient_Client = Client('http://localhost:8080/hospitalServices/PatientMethodsService?WSDL')



def java_returnPatientByAMKA(amka):
    result = java_Patient_Client.service.returnPatientByAMKA(amka)
    return not(result == None)

def register_user(request):
    print java_Patient_Client
    if request.method == 'POST':
        form = PatientnForm(request.POST)
        if form.is_valid():
            if java_returnPatientByAMKA(form.cleaned_data['amka']):
                # if the user does not exist
                return HttpResponseRedirect('/accounts/register_failed')

            # else save him
            pat = java_Patient_Client.factory.create('patient')
            pat.email =form.cleaned_data['email']
            pat.AMKA =form.cleaned_data['amka']
            pat.patientName =form.cleaned_data['patientName']
            pat.patientSurname =form.cleaned_data['patientSurname']
            pat.patientGender =form.cleaned_data['patientGender']
            pat.insuranceFund =form.cleaned_data['insuranceFund']
            pat.bloodType =form.cleaned_data['bloodType']
            pat.address =form.cleaned_data['address']
            pat.country =form.cleaned_data['country']


            response =  java_Patient_Client.service.insertPatient(pat)
            if (response):
                form.save()
            return HttpResponseRedirect('/accounts/register_success')

    else:
        form = PatientnForm()
    args = {}
    args.update(csrf(request))

    args['form'] = form

    return render_to_response('register_pat.html', args)



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




def register_success(request):
    return render_to_response('register_success.html')


def register_failed(request):
    return render_to_response('register_failed.html')

