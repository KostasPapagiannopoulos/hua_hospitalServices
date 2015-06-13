from django.db import models

class How(models.Model):
    title = models.CharField(max_length = 140)
    body = models.TextField()

    def _unicode_ (self):
        return self.title
    
# Create your models here.
