

from django.conf.urls import patterns, url
from hospital import views

urlpatterns = patterns('',
        url(r'^$', views.index, name='index'),
        url(r'^showusers', views.showusers,),
        url(r'^showpatients', views.showpatients,),
        url(r'^clinics', views.clinics,),
        url(r'^doctors', views.doctors,),
        url(r'^duty/(?P<doctorid>[0-9]+)', views.duty,),
        url(r'^appointments/(?P<doctorid>[0-9]+)', views.appointments,),
        url(r'^home_doctor/', views.home_doctor,),
        url(r'^pendingappointments/', views.pendingappointments,),
        url(r'^appointment/(?P<appointmentID>[0-9]+)', views.appointment_edit,),
        url(r'^pendingappointments_director/', views.appointments_director,),
         url(r'^appointment_by_director/(?P<appointmentID>[0-9]+)', views.appointment_edit_by_director,),





    )

