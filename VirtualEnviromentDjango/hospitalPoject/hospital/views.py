from django.http import HttpResponse
from django.shortcuts import render
from suds.client import Client


def index(request):
    return HttpResponse("Rango says Rango")


#soap_client = Client('http://10.100.51.100:8080/lab3/UsersWS?WSDL')
def showusers(request):
   return HttpResponse("Rango says Rango")
#   results = soap_client.service.showUsers()
#   context = { 'results':results, }
#   return render(request, 'allusers.html', context)


