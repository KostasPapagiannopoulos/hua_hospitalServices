#!/usr/bin/env python
# -*- coding: utf-8 -*-
from django.http import HttpResponse
from django.shortcuts import render
from suds.client import Client
from django.shortcuts import render_to_response
from django.http import HttpResponseRedirect
from django.contrib import auth
from django.core.context_processors import csrf
from forms import PatientnForm, NewAppointmentForm
from django.contrib.auth.decorators import login_required, user_passes_test
import urllib2


java_Patient_Client = Client('http://localhost:8080/hospitalServices/PatientMethodsService?WSDL')
java_Appointment_Client = Client('http://localhost:8080/hospitalServices/AppointmentMethodsService?WSDL')


def java_returnPatientByAMKA(amka, username):
    result = java_Patient_Client.service.returnPatientByAMKA(amka, username)
    return not (result == None)


def register_user(request):
    print java_Patient_Client
    if request.method == 'POST':
        form = PatientnForm(request.POST)
        if form.is_valid():
            if java_returnPatientByAMKA(form.cleaned_data['amka'], form.cleaned_data['username']):
                # if the user does not exist
                return HttpResponseRedirect('/pat/register_failed')

            # else save him
            pat = java_Patient_Client.factory.create('patient')

            pat.username = form.cleaned_data['username']
            pat.email = form.cleaned_data['email']
            pat.AMKA = form.cleaned_data['amka']
            pat.patientName = form.cleaned_data['patientName']
            pat.patientSurname = form.cleaned_data['patientSurname']
            pat.patientGender = form.cleaned_data['patientGender']
            pat.insuranceFund = form.cleaned_data['insuranceFund']
            pat.bloodType = form.cleaned_data['bloodType']
            pat.address = form.cleaned_data['address']
            pat.country = form.cleaned_data['country']

            response = java_Patient_Client.service.insertPatient(pat)
            if (response):
                form.save()
            return HttpResponseRedirect('/pat/register_pat_success')

    else:
        form = PatientnForm()
    args = {}
    args.update(csrf(request))

    args['form'] = form

    return render_to_response('register_pat.html', args)


def login(request):
    c = {}
    c.update(csrf(request))
    return render_to_response('login_pat.html', c)


def auth_view(request):
    username = request.POST.get('username', '')
    password = request.POST.get('password', '')
    user = auth.authenticate(username=username, password=password)

    if user is not None:
        auth.login(request, user)
        if request.method == 'GET' and 'next' in request.GET:
            url_with_get = urllib2.unquote(request.GET.get('next'))
            if(url_with_get != ''):
                return HttpResponseRedirect(url_with_get)
        return HttpResponseRedirect('/pat/loggedin')
    else:
        return HttpResponseRedirect('/pat/invalid')


def loggedin(request):
    return render_to_response('loggedin.html',
                              {'full_name': request.user.username})


def invalid_login(request):
    return render_to_response('invalid_login.html')


def logout(request):
    auth.logout(request)
    return render_to_response('logout.html')


def register_pat_success(request):
    return render_to_response('register_pat_success.html')


def register_failed(request):
    return render_to_response('register_failed.html')


def user_in_patient_group(user):
    if user:
        return  user.groups.filter(name='patient').count() != 0
    return False

@login_required(login_url='/pat/login/')
@user_passes_test(user_in_patient_group, login_url='/pat/login/')
def new_appointment(request):
    currentPatient  = java_Patient_Client.service.returnPatientByUsername(request.user.username)

    if currentPatient is None:
        return HttpResponse("User Not Found!")
    if request.method == 'GET':
        form = NewAppointmentForm(patient=currentPatient)
    else:
        appointment = java_Appointment_Client.factory.create('appointment')

        form = NewAppointmentForm(request.POST, patient=currentPatient)
        if form.is_valid():
            appointment.patientName = form.cleaned_data['patientName']
            appointment.patientSurname = form.cleaned_data['patientSurname']
            appointment.AMKA = form.cleaned_data['AMKA']
            appointment.insuranceFund = form.cleaned_data['insuranceFund']
            appointment.clinicid = form.cleaned_data['clinicid']
            appointment.diseaseDetails = form.cleaned_data['diseaseDetails']
            appointment.strAppointmentDate = form.cleaned_data['appointmentDate']
            appointment.strAppointmentTime = form.cleaned_data['appointmentTime']
            appointment.appointmentEmergency = form.cleaned_data['appointmentEmergency']
            result = java_Appointment_Client.service.insertAppointment(appointment)
            if result:
                 return HttpResponseRedirect('/pat/appointment/all')
            else:
                return HttpResponse("Request Not Created")
    return render(request, 'appointment.html', {'form': form, 'action_url': '/pat/appointment/new'})



@login_required(login_url='/pat/login/')
@user_passes_test(user_in_patient_group, login_url='/pat/login/')
def all_appointments(request):
   currentPatient  = java_Patient_Client.service.returnPatientByUsername(request.user.username)

   results = java_Appointment_Client.service.returnAppointmentByAMKA(currentPatient.AMKA)
   context = { 'results':results, 'patient': currentPatient}
   return render(request, 'allappointments.html', context)



@login_required(login_url='/pat/login/')
@user_passes_test(user_in_patient_group, login_url='/pat/login/')
def appointment_edit_by_user(request, appointmentID):
    result = java_Appointment_Client.service.patientRequestExpeditionAppointment(appointmentID)
    if not result:
        return HttpResponse("Κάποιο πρόβλημα προέκυψε")
    else:
        return HttpResponseRedirect('/pat/appointment/all')


@login_required(login_url='/pat/login/')
@user_passes_test(user_in_patient_group, login_url='/pat/login/')
def appointment_edit_by_user(request, appointmentID):
    result = java_Appointment_Client.service.patientRequestExpeditionAppointment(appointmentID)
    if not result:
        return HttpResponse("Κάποιο πρόβλημα προέκυψε")
    else:
        return HttpResponseRedirect('/pat/appointment/all')

