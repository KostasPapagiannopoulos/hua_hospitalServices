#!/usr/bin/env python
# -*- coding: utf-8 -*-
from django import forms
from django.contrib.auth.models import User
from django.contrib.auth.forms import UserCreationForm
from django.contrib.auth.models import Group
from suds.client import Client
from datetime import datetime, timedelta
from django.utils.dateformat import DateFormat

USER_TYPE_CHOICES = (
    ('1', 'Patient'),
    ('2', 'Staff'),
    ('3', 'Doctor'),
)

GENDER_CHOICES = (
    ('M', 'Male'),
    ('F', 'Female'),
)


class MyRegistrationForm(UserCreationForm):
    email = forms.EmailField(required=True)
    first_name = forms.CharField(required=True)
    last_name = forms.CharField(required=True)
    user_type = forms.ChoiceField(choices=USER_TYPE_CHOICES)
    gender = forms.ChoiceField(choices=GENDER_CHOICES)
    amka = forms.IntegerField(required=True)


    class Meta:
        model = User
        fields = ('user_type',
                  'first_name',
                  'last_name',
                  'gender',
                  'amka',
                  'username',
                  'email',
                  'password1',
                  'password2')
        
    def save(self, commit=True):
        user = super(MyRegistrationForm, self).save(commit=False)
        user.email = self.cleaned_data['email']
        user.first_name = self.cleaned_data['first_name']
        user.last_name = self.cleaned_data['last_name']
        if self.cleaned_data['user_type'] != 1: #if is not patient, it needs activation from admin
            user.is_active = False
        group = [i for i, v in enumerate(USER_TYPE_CHOICES) if v[0] == self.cleaned_data['user_type']]
        g = Group.objects.get(name=USER_TYPE_CHOICES[group[0]][1])

        if commit:
            user.save()
            g.user_set.add(user)

        return user




EMERGENCY_CHOICES = (
    ('1', "Κανονικό"),
    ('2', "Επείγον"),
)



java_Clinic_services = Client('http://localhost:8080/hospitalServices/ClinicMethodsService?WSDL')

class EditAppointmentForm(forms.Form):
    dt = datetime.now() + timedelta(days=1)
    df = DateFormat(dt)

    #get clinics
    results = java_Clinic_services.service.returnAllClinics()
    clinic_choices = [(i.clinicid, i.clinicType + ' ('+i.clinicName + ') ' ) for i in results]

    patientName = forms.CharField(label='Όνομα', max_length=100, required=True, widget=forms.TextInput(attrs={'class':'disabled', 'readonly':'readonly'}))
    patientSurname = forms.CharField(label='Επώνυμο', max_length=100, required=True, widget=forms.TextInput(attrs={'class':'disabled', 'readonly':'readonly'}))
    AMKA = forms.IntegerField(label='ΑΜΚΑ', required=True, widget=forms.TextInput(attrs={'class':'disabled', 'readonly':'readonly'}))
    diseaseDetails = forms.CharField(label='Ποιό είναι το πρόβλημα σας', max_length=100, required=True, widget=forms.TextInput(attrs={'class':'disabled', 'readonly':'readonly'}))
    clinicid = forms.ChoiceField(label='Κλινική', required=True, choices=clinic_choices)
    insuranceFund = forms.CharField(label='Ασφάλεια', max_length=100, required=True, widget=forms.TextInput(attrs={'class':'disabled', 'readonly':'readonly'}))
    appointmentDate = forms.DateField(label="Ημερομηνία", required=True, input_formats=['%d/%m/%Y'],
                                      initial=df.format('d/m/Y'))
    appointmentTime = forms.TimeField(label="Ώρα", required=True, input_formats=['%H:%M'], initial=df.format('H:i'))
    appointmentEmergency = forms.ChoiceField(label='Είδος Ραντεβού', required=False, choices=EMERGENCY_CHOICES, widget=forms.Select(attrs={'disabled':'disabled', 'readonly':'readonly'}))

    #with the following code we set the initial values from the 'patient' argument
    def __init__(self, *args, **kwargs):
        appointment = kwargs.pop('appointment')
        super(EditAppointmentForm, self).__init__(*args, **kwargs)
        self.fields['patientName'].initial=appointment.patientName
        self.fields['patientSurname'].initial=appointment.patientSurname
        self.fields['AMKA'].initial=appointment.AMKA
        self.fields['insuranceFund'].initial=appointment.insuranceFund
        self.fields['diseaseDetails'].initial=appointment.diseaseDetails
        self.fields['clinicid'].initial=appointment.clinicid
        self.fields['appointmentDate'].initial=appointment.appointmentDate #DateFormat(datetime.strptime(appointment.appointmentDate, 'd/m/Y')).format('d/m/Y')
        self.fields['appointmentTime'].initial=appointment.appointmentTime
        self.fields['appointmentEmergency'].initial=appointment.appointmentEmergency

