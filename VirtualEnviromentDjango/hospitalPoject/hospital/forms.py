from django import forms
from django.contrib.auth.models import User
from django.contrib.auth.forms import UserCreationForm
from django.contrib.auth.models import Group

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

class ContactForm1(forms.Form):
    subject = forms.CharField(max_length=100)
    
class ContactForm2(forms.Form):
    sender = forms.EmailField()

class ContactForm3(forms.Form):
    message = forms.CharField(widget=forms.Textarea)
    
    
