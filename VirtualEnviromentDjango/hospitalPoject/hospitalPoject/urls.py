from django.conf.urls import patterns, include, url
from django.contrib import admin




urlpatterns = patterns('',
    # Examples:
    url(r'^$', include('intro.urls')),
    url(r'^intro/', include('intro.urls')),
     url(r'^how/', include('how.urls')),                    
    # url(r'^blog/', include('blog.urls')),
    #url(r'^$', 'home.views.index'),
    url(r'^admin/', include(admin.site.urls)),
    url(r'^hospital/', include('hospital.urls')),
    #url(r'^accounts/', include('registration.backends.default.urls')),
    (r'^accR/', include('registration.backends.simple.urls')),
                       

    (r'^accounts/login/$', 'hospital.views.login'),
    (r'^accounts/auth/$', 'hospital.views.auth_view'),
    (r'^accounts/logout/$', 'hospital.views.logout'),
    (r'^accounts/loggedin/$', 'hospital.views.loggedin'),
    (r'^accounts/invalid', 'hospital.views.invalid_login'),
    (r'^accounts/register/$', 'hospital.views.register_user'),
    (r'^accounts/register_success/$', 'hospital.views.register_success'),
    (r'^accounts/register_failed/$', 'hospital.views.register_failed'),

    url(r'^pat/', include('patient.urls'),),


)
