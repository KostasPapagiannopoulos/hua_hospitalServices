from django.conf.urls import patterns, include, url
from django.views.generic import ListView
from how.models import How
from how import views


urlpatterns = patterns ('',
   
          url(r'^', ListView.as_view(
              queryset=How.objects.all().order_by("-title")[:10],
          template_name="how.html")),                   
                   
)
