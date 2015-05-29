from django.conf.urls import patterns, include, url
from django.contrib import admin

urlpatterns = patterns('',
    # Examples:
    # url(r'^$', 'hospitalPoject.views.home', name='home'),
    # url(r'^blog/', include('blog.urls')),

    url(r'^admin/', include(admin.site.urls)),
    url(r'^hospital/', include('hospital.urls')),
    #url(r'^accounts/', include('registration.backends.default.urls')),
    (r'^accounts/', include('registration.backends.simple.urls')),
)
