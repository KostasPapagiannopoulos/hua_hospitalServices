

from django.conf.urls import patterns, url
from hospital import views

urlpatterns = patterns('',
        url(r'^$', views.index, name='index'),
        url(r'^showusers', views.showusers,),
        url(r'^showpatients', views.showpatients,),
        url(r'^clinics', views.clinics,),
        url(r'^doctors', views.doctors,),
        url(r'^duty/(?P<doctorid>[0-9]+)', views.duty,),





    )

