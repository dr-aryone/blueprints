// Exported from:        http://juliano-mbp.local:5516/#/templates/Folderc8ddd97b0de74d65b288e746d1bbbbb2-Releasedd48169436124943951a1e818ef66c0a/releasefile
// XL Release version:   8.6.1
// Date created:         Tue Apr 09 09:30:00 CEST 2019

xlr {
  template('Release Blueprints') {
    folder('Blueprints')
    scheduledStartDate Date.parse("yyyy-MM-dd'T'HH:mm:ssZ", '2019-04-08T09:00:00+0200')
    phases {
      phase('New Phase') {
        tasks {
          script('Check Travis CI build') {
            script (['''\
import urllib2
import json
import time

travis_url = 'https://api.travis-ci.org/repos/xebialabs/blueprints'
headers = {"User-Agent":"XebiaLabs", "Accept": "application/vnd.travis-ci.2+json", "Authorization": "token 6dBuPxRSmn5yBhslbcfb0w" }

def make_req(url, body=None, token=None):
  req = urllib2.Request(url, None, headers=headers)
  opener = urllib2.build_opener()
  response = opener.open(req)
  return json.loads(response.read())

repo = make_req(travis_url)['repo']
print repo
print("\\nLast build #: " + repo['last_build_number'])
print("\\nLast build status: " + repo['last_build_state'])
if repo['last_build_state'] != "passed":
  print("\\nCheck the logs on: https://travis-ci.org/xebialabs/blueprints/builds/" + repo['last_build_id'])
  exit(1)
'''])
          }
        }
      }
    }
    
  }
}