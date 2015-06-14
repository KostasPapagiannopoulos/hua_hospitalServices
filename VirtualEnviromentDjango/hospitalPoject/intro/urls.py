from django.conf.urls import patterns, include, url
from django.views.generic import ListView
from intro.models import Intro
from intro import views


urlpatterns = patterns ('',
   
          url(r'^', ListView.as_view(
              queryset=Intro.objects.all().order_by("-title")[:10],
          template_name="home.html")),    
                        
                         url(r'^intro', views.newsFeed,),               
                   
)
