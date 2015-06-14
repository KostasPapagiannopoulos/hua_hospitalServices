import urllib2
import base64
import json

from django.http import HttpResponse
from django.shortcuts import render
from suds.client import Client
from django.http import HttpResponseRedirect
from django.core.context_processors import csrf
from forms import MyRegistrationForm, EditAppointmentForm, EditAppointmentForm_by_director, \
    EditExpeditionFormByDirector, DoctorEditApp
from django.contrib.auth.decorators import login_required, user_passes_test
from django.contrib import auth

java_Patient_Client = Client('http://localhost:8080/hospitalServices/PatientMethodsService?WSDL')
java_Staff_Client = Client('http://localhost:8080/hospitalServices/HospitalStaffMethodsService?WSDL')
soap_client_ClinicServices = Client('http://localhost:8080/hospitalServices/ClinicMethodsService?WSDL')
java_Appointment_Client = Client('http://localhost:8080/hospitalServices/AppointmentMethodsService?WSDL')


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
    return render(request, 'login.html', c)


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
    return render(request, 'loggedin.html',
                  {'full_name': request.user.username, 'auth': request.user.is_authenticated})


def invalid_login(request):
    return render(request, 'invalid_login.html')


def logout(request):
    auth.logout(request)
    return render(request, 'logout.html')


def register_user(request):
    if request.method == 'POST':
        form = MyRegistrationForm(request.POST)
        if form.is_valid():
            # if not (form.cleaned_data['user_type'] == 'Patient'):
            #    # if its a patient, we dont need to check his username
            #    response = java_insertUser(form)
            # else:
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

    return render(request, 'register.html', args)


def java_insertUser(form):
    patient = java_Patient_Client.factory.create('patient')
    patient.patientName = form.cleaned_data['first_name']
    patient.patientSurname = form.cleaned_data['last_name']
    patient.patientGender = form.cleaned_data['gender']
    patient.insuranceFund = 'insuranceFund'
    patient.AMKA = form.cleaned_data['amka']
    patient.bloodType = 'bloodType'
    patient.address = 'address'
    patient.country = 'country'
    patient.email = form.cleaned_data['email']
    return java_Patient_Client.service.insertPatient(patient) != "Error"


def java_insertStaff(form):
    hospitalstaff = java_Staff_Client.factory.create('hospitalStaff')

    hospitalstaff.firstName = form.cleaned_data['first_name']
    hospitalstaff.lastSurname = form.cleaned_data['last_name']
    hospitalstaff.gender = form.cleaned_data['gender']
    hospitalstaff.birthDate = '2015-05-01'
    hospitalstaff.staffType = form.cleaned_data['user_type']
    hospitalstaff.emp_no = form.cleaned_data['username']
    hospitalstaff.specialty = form.cleaned_data['specialty']


    return java_Staff_Client.service.insertStaff(hospitalstaff) != "Error"


def register_success(request):
    return render(request, 'register_success.html')


def register_failed(request):
    return render(request, 'register_failed.html')


soap_client_PatientServices = Client('http://localhost:8080/hospitalServices/PatientMethodsService?WSDL')


def user_in_patient_group(user):
    if user:
        return user.groups.filter(name='patient').count() != 0
    return False


def user_in_doctor_group(user):
    if user:
        return user.groups.filter(name='doctor').count() != 0
    return False


def user_in_staff_group(user):
    if user:
        return user.groups.filter(name='staff').count() != 0
    return False


def user_in_director_group(user):
    if user:
        return user.groups.filter(name='director').count() != 0
    return False

def user_in_staff_or_doctor_or_director_group(user):
    return user_in_doctor_group(user) or user_in_staff_group(user) or user_in_director_group(user)



@login_required(login_url='/accounts/login/')
@user_passes_test(user_in_staff_group, login_url='/accounts/login/')
def showpatients(request):
    results = soap_client_PatientServices.service.returnAllPatients()
    context = {'results': results, }
    return render(request, 'allpatients.html', context)


def clinics(request):
    results = soap_client_ClinicServices.service.returnAllClinics()
    context = {'results': results, }
    return render(request, 'allclinics.html', context)


def doctors(request):
    results = soap_client_ClinicServices.service.returnAllDoctors()
    context = {'results': results, }
    return render(request, 'alldoctors.html', context)


@login_required(login_url='/accounts/login/')
@user_passes_test(user_in_staff_or_doctor_or_director_group, login_url='/accounts/login/')
def duty(request, doctorid):
    results = soap_client_ClinicServices.service.returnAllClinicDuty(doctorid)
    context = {'results': results, }
    return render(request, 'allduty.html', context)


@login_required(login_url='/accounts/login/')
@user_passes_test(user_in_doctor_group, login_url='/accounts/login/')
def appointments(request, doctorid):
    results = soap_client_ClinicServices.service.returnDoctorAppointments(doctorid)
    context = {'results': results, }
    return render(request, 'alldoctorappoitments.html', context)


@login_required(login_url='/accounts/login/')
@user_passes_test(user_in_doctor_group, login_url='/accounts/login/')
def home_doctor(request):
    appointments = soap_client_ClinicServices.service.returnDoctorAppointments(request.user.username)
    duty = soap_client_ClinicServices.service.returnAllClinicDuty(request.user.username)
    doctor = java_Staff_Client.service.returnStaffByEmpNo(request.user.username)
    context = {'appointments': appointments, 'duty': duty, 'doctor': doctor}
    return render(request, 'doctor_home.html', context)


@login_required(login_url='/accounts/login/')
@user_passes_test(user_in_staff_group, login_url='/accounts/login/')
def pendingappointments(request):
    appointments = java_Appointment_Client.service.returnAllAppointments()
    context = {'appointments': appointments}
    return render(request, 'pendingappointments.html', context)


@login_required(login_url='/accounts/login/')
@user_passes_test(user_in_doctor_group, login_url='/accounts/login/')
def patientHistory(request, amka):
    history = soap_client_ClinicServices.service.getAssessmentsByPatientAMKA(amka)
    currentPatient  = java_Patient_Client.service.returnPatientByAMKA(amka, None)

    context = {'history': history, 'patient': currentPatient}
    return render(request, 'patientHistory.html', context)


@login_required(login_url='/accounts/login/')
@user_passes_test(user_in_director_group, login_url='/accounts/login/')
def appointments_director(request):
    appointments = java_Appointment_Client.service.returnAllAppointments_Director()
    context = {'appointments': appointments}
    return render(request, 'pendingappointments_director.html', context)


@login_required(login_url='/accounts/login/')
@user_passes_test(user_in_staff_group, login_url='/accounts/login/')
def appointment_edit(request, appointmentID):
    currentAppointment = java_Appointment_Client.service.returnAppointmentById(appointmentID)

    if currentAppointment is None:
        return HttpResponse("Appointment Not Found!")
    if request.method == 'GET':
        form = EditAppointmentForm(appointment=currentAppointment)
    else:
        appointment = java_Appointment_Client.factory.create('appointment')

        form = EditAppointmentForm(request.POST, appointment=currentAppointment)
        if form.is_valid():
            appointment.appointmentID = appointmentID
            appointment.patientName = form.cleaned_data['patientName']
            appointment.patientSurname = form.cleaned_data['patientSurname']
            appointment.AMKA = form.cleaned_data['AMKA']
            appointment.insuranceFund = form.cleaned_data['insuranceFund']
            appointment.clinicid = form.cleaned_data['clinicid']
            appointment.diseaseDetails = form.cleaned_data['diseaseDetails']
            appointment.strAppointmentDate = form.cleaned_data['appointmentDate']
            appointment.strAppointmentTime = form.cleaned_data['appointmentTime']
            appointment.appointmentEmergency = form.cleaned_data['appointmentEmergency']
            result = java_Appointment_Client.service.staffUpdatesAppointment(appointment)
            if result:
                return HttpResponseRedirect('/hospital/pendingappointments')

            else:
                return HttpResponse("Appointment Not Updated")
    return render(request, 'appointment.html',
                  {'form': form, 'action_url': '/hospital/appointment/' + appointmentID})


@login_required(login_url='/accounts/login/')
@user_passes_test(user_in_director_group, login_url='/accounts/login/')
def appointment_edit_by_director(request, appointmentID):
    currentAppointment = java_Appointment_Client.service.returnAppointmentById(appointmentID)

    if currentAppointment is None:
        return HttpResponse("Appointment Not Found!")
    if request.method == 'GET':
        form = EditAppointmentForm_by_director(appointment=currentAppointment)
    else:
        appointment = java_Appointment_Client.factory.create('appointment')

        form = EditAppointmentForm_by_director(request.POST, appointment=currentAppointment)
        if form.is_valid():
            appointment.appointmentID = appointmentID
            appointment.patientName = form.cleaned_data['patientName']
            appointment.patientSurname = form.cleaned_data['patientSurname']
            appointment.AMKA = form.cleaned_data['AMKA']
            appointment.insuranceFund = form.cleaned_data['insuranceFund']
            appointment.clinicid = form.cleaned_data['clinicid']
            appointment.diseaseDetails = form.cleaned_data['diseaseDetails']
            appointment.strAppointmentDate = form.cleaned_data['appointmentDate']
            appointment.strAppointmentTime = form.cleaned_data['appointmentTime']
            appointment.appointmentEmergency = form.cleaned_data['appointmentEmergency']
            appointment.rejectReasons = form.cleaned_data['rejectReasons']
            result = java_Appointment_Client.service.HeadUpdatesAppointment(appointment)
            if result:
                return HttpResponseRedirect('/hospital/pendingappointments_director')
            else:
                return HttpResponse("Appointment Not Updated")
    return render(request, 'appointment.html',
                  {'form': form, 'action_url': '/hospital/appointment_by_director/' + appointmentID})


@login_required(login_url='/accounts/login/')
@user_passes_test(user_in_director_group, login_url='/accounts/login/')
def appointment_edit_by_director_expedition(request, appointmentID):
    currentAppointment = java_Appointment_Client.service.returnAppointmentById(appointmentID)

    if currentAppointment is None:
        return HttpResponse("Appointment Not Found!")
    if request.method == 'GET':
        form = EditExpeditionFormByDirector(appointment=currentAppointment)
    else:
        appointment = java_Appointment_Client.factory.create('appointment')

        form = EditExpeditionFormByDirector(request.POST, appointment=currentAppointment)
        if form.is_valid():
            appointment.appointmentID = appointmentID
            appointment.patientName = form.cleaned_data['patientName']
            appointment.patientSurname = form.cleaned_data['patientSurname']
            appointment.AMKA = form.cleaned_data['AMKA']
            appointment.insuranceFund = form.cleaned_data['insuranceFund']
            appointment.clinicid = form.cleaned_data['clinicid']
            appointment.diseaseDetails = form.cleaned_data['diseaseDetails']
            appointment.strAppointmentDate = form.cleaned_data['appointmentDate']
            appointment.strAppointmentTime = form.cleaned_data['appointmentTime']
            appointment.appointmentEmergency = form.cleaned_data['appointmentEmergency']
            appointment.rejectReasons = form.cleaned_data['rejectReasons']
            expeditionAccepted = form.cleaned_data['expeditionAccepted']
            result = java_Appointment_Client.service.directorAcceptExpeditionAppointment(appointment,
                                                                                         expeditionAccepted)
            if result:
                return HttpResponseRedirect('/hospital/pendingappointments_director')
            else:
                return HttpResponse("Appointment Not Updated")
    return render(request, 'appointment.html',
                  {'form': form, 'action_url': '/hospital/appointment_edit_by_director_expedition/' + appointmentID})


@login_required(login_url='/accounts/login/')
@user_passes_test(user_in_doctor_group, login_url='/accounts/login/')
def doctor_edit_app(request, appointmentID):
    currentAppointment = java_Appointment_Client.service.returnAppointmentById(appointmentID)
    currentPatient  = java_Patient_Client.service.returnPatientByAMKA(currentAppointment.AMKA, None)

    if currentAppointment is None:
        return HttpResponse("Appointment Not Found!")
    if request.method == 'GET':
        form = DoctorEditApp(appointment=currentAppointment)
    else:
        print soap_client_ClinicServices
        assessment = soap_client_ClinicServices.factory.create('assessment')
        doctor = java_Staff_Client.service.returnStaffByEmpNo(request.user.username)

        form = DoctorEditApp(request.POST, appointment=currentAppointment)
        if form.is_valid():
            assessment.appointmentId = currentAppointment.appointmentID
            assessment.doctorId = doctor.emp_no
            assessment.problem = form.cleaned_data['problem']
            assessment.subjective = form.cleaned_data['subjective']
            assessment.objective = form.cleaned_data['objective']
            assessment.assessment = form.cleaned_data['assessment']
            assessment.plan = form.cleaned_data['plan']

            result = soap_client_ClinicServices.service.doctorInsertAppointment(assessment)
            if result:
                return HttpResponseRedirect('/hospital/home_doctor')
            else:
                return HttpResponse("Appointment Not Updated")
    return render(request, 'appointment.html',
                  {'form': form, 'patient': currentPatient, 'action_url': '/hospital/doctor_edit_app/' + appointmentID})
