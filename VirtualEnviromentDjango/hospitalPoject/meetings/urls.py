from django.conf.urls import patterns, include, url
from django.views.generic import ListView
from meetings.models import Meeting
from meetings import views


urlpatterns = patterns ('',
   
          url(r'^', ListView.as_view(
              queryset=Meeting.objects.all().order_by("-title")[:20],
          template_name="meetings.html")),    
                        
                                     
                   
)
