from django import forms
from django.contrib.auth.models import User
from django.contrib.auth.forms import UserCreationForm


class PatientnForm(UserCreationForm):
    email = forms.EmailField(required=True)
    amka = forms.IntegerField(required=True)
    patientName = forms.CharField(required=True)
    patientSurname = forms.CharField(required = True)
    patientGender  = forms.CharField(required = True) # TODO:  to become dropdown
    insuranceFund= forms.CharField(required = True)
    bloodType= forms.CharField(required = True)
    address= forms.CharField(required = True)
    country= forms.CharField(required = True)

    class Meta:
        model = User
        fields = ('username', 'email', 'password1', 'password2', 'amka', 'patientName', 'patientSurname', 'patientGender', 'insuranceFund', 'bloodType',
                  'address', 'country', )
        
    def save(self, commit=True):
        user = super(PatientnForm, self).save(commit=False)
        user.email = self.cleaned_data['email']
        #user.amka = self.cleaned_data['amka']

        # user.set_password(self.cleaned_data['password1'])
        
        if commit:
            user.save()
            
        return user
    
    

class ContactForm1(forms.Form):
    subject = forms.CharField(max_length=100)
    
class ContactForm2(forms.Form):
    sender = forms.EmailField()

class ContactForm3(forms.Form):
    message = forms.CharField(widget=forms.Textarea)
    
    
