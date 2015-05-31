

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
        (r'^register_success/$', views.register_success),
        (r'^register_failed/$', views.register_failed),


    )

