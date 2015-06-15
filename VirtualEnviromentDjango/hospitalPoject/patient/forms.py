#!/usr/bin/env python
# -*- coding: utf-8 -*-

from django import forms
from django.contrib.auth.models import User, Group
from django.contrib.auth.forms import UserCreationForm
from datetime import datetime, timedelta
from django.utils.dateformat import DateFormat
from suds.client import Client


GENDER_CHOICES = (
    ('', ''),
    ('M', "Άνδρας"),
    ('F', "Γυναίκα"),
)

BLOOD_TYPE_CHOICES = (
    ('', ''),
    ('O+', 'O+'),
    ('A+', 'A+'),
    ('B+', 'B+'),
    ('B+', 'B+'),
    ('AB+', 'AB+'),
    ('O−', 'O−'),
    ('A−', 'A−'),
    ('B−', 'B−'),
    ('AB−', 'AB−'),
)


class PatientnForm(UserCreationForm):
    email = forms.EmailField(label='Email',required=True)
    amka = forms.IntegerField(label='ΑΜΚΑ',required=True)
    patientName = forms.CharField(label='Όνομα',required=True)
    patientSurname = forms.CharField(label='Επώνυμο',required=True)
    patientGender = forms.ChoiceField(label='Φύλο',required=True, choices=GENDER_CHOICES)
    insuranceFund = forms.CharField(label='Ασφάλιστικός φορέας',required=True)
    bloodType = forms.ChoiceField(label='Ομάδα Αίματος',required=True, choices=BLOOD_TYPE_CHOICES)
    address = forms.CharField(label='Διεύθυνση',required=True)
    country = forms.CharField(label='Χώρα',required=True)

    class Meta:
        model = User
        fields = (
            'username', 'email', 'password1', 'password2', 'amka', 'patientName', 'patientSurname', 'patientGender',
            'insuranceFund', 'bloodType',
            'address', 'country',)

    def save(self, commit=True):
        user = super(PatientnForm, self).save(commit=False)
        user.email = self.cleaned_data['email']
        #user.amka = self.cleaned_data['amka']
        user.first_name = self.cleaned_data['patientName']
        user.last_name = self.cleaned_data['patientSurname']
        user.is_staff = False


        # user.set_password(self.cleaned_data['password1'])

        if commit:
            user.save()
            # add user to patient group
            g = Group.objects.get(name='patient')
            g.user_set.add(user)

        return user


class ContactForm1(forms.Form):
    subject = forms.CharField(max_length=100)


class ContactForm2(forms.Form):
    sender = forms.EmailField()


class ContactForm3(forms.Form):
    message = forms.CharField(widget=forms.Textarea)


EMERGENCY_CHOICES = (
    ('1', "Κανονικό"),
    ('2', "Επείγον"),
)



java_Clinic_services = Client('http://localhost:8080/hospitalServices/ClinicMethodsService?WSDL')

class NewAppointmentForm(forms.Form):
    dt = datetime.now() + timedelta(days=1)
    df = DateFormat(dt)

    #get clinics
    results = java_Clinic_services.service.returnAllClinics()
    clinic_choices = [(i.clinicid, i.clinicType + ' ('+i.clinicName + ') ' ) for i in results]

    patientName = forms.CharField(label='Όνομα', max_length=100, required=True, widget=forms.TextInput(attrs={'class':'disabled', 'readonly':'readonly'}))
    patientSurname = forms.CharField(label='Επώνυμο', max_length=100, required=True, widget=forms.TextInput(attrs={'class':'disabled', 'readonly':'readonly'}))
    AMKA = forms.IntegerField(label='ΑΜΚΑ', required=True, widget=forms.TextInput(attrs={'class':'disabled', 'readonly':'readonly'}))
    diseaseDetails = forms.CharField(label='Ποιό είναι το πρόβλημα σας', max_length=100, required=True)
    clinicid = forms.ChoiceField(label='Κλινική', required=True, choices=clinic_choices)
    insuranceFund = forms.CharField(label='Ασφάλεια', max_length=100, required=True)
    appointmentDate = forms.DateField(label="Ημερομηνία", required=True, input_formats=['%d/%m/%Y'],
                                      initial=df.format('d/m/Y'))
    appointmentTime = forms.TimeField(label="Ώρα", required=True, input_formats=['%H:%M'], initial=df.format('H:i'))
    appointmentEmergency = forms.ChoiceField(label='Είδος Ραντεβού', required=True, choices=EMERGENCY_CHOICES)

    #with the following code we set the initial values from the 'patient' argument
    def __init__(self, *args, **kwargs):
        patient = kwargs.pop('patient')
        super(NewAppointmentForm, self).__init__(*args, **kwargs)
        self.fields['patientName'].initial=patient.patientName
        self.fields['patientSurname'].initial=patient.patientSurname
        self.fields['AMKA'].initial=patient.AMKA
        self.fields['insuranceFund'].initial=patient.insuranceFund


