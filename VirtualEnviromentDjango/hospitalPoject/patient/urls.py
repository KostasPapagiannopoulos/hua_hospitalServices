

from django.conf.urls import patterns, url
from patient import views

urlpatterns = patterns('',
       # url(r'^$', views.index, name='index'),

        (r'^login/$', views.login),
        (r'^auth/$', views.auth_view),
        (r'^logout/$', views.logout),
        (r'^loggedin/$', views.loggedin),
        (r'^invalid', views.invalid_login),
        (r'^register/$', views.register_user),
        (r'^register_pat_success/$', views.register_pat_success),
        (r'^register_failed/$', views.register_failed),

        (r'^appointment/new$', views.new_appointment),
        (r'^appointment/all$', views.all_appointments),
        (r'^appointment_edit_by_user/(?P<appointmentID>[0-9]+)$', views.appointment_edit_by_user),



    )

