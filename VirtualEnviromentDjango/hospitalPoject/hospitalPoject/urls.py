from django.conf.urls import patterns, include, url
from django.contrib import admin
from hospital import views

urlpatterns = patterns('',
    # Examples:
    # url(r'^$', 'hospitalPoject.views.home', name='home'),
    # url(r'^blog/', include('blog.urls')),

    url(r'^admin/', include(admin.site.urls)),
    url(r'^hospital/', include('hospital.urls')),
    #url(r'^accounts/', include('registration.backends.default.urls')),
    (r'^accR/', include('registration.backends.simple.urls')),

    (r'^accounts/login/$', views.login),
    (r'^accounts/auth/$', views.auth_view),
    (r'^accounts/logout/$', views.logout),
    (r'^accounts/loggedin/$', views.loggedin),
    (r'^accounts/invalid', views.invalid_login),
    (r'^accounts/register/$', 'hospital.views.register_user'),
    (r'^accounts/register_success/$', views.register_success),
    (r'^accounts/register_failed/$', views.register_failed),



)
