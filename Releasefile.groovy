// Exported from:        https://xl-release.xebialabs.com/#/templates/Folderdbce503315164ec3848b3058fcb37524-Releasea6867c97994c4ddc9671845ef0ed0de4/releasefile
// XL Release version:   8.6.1
// Date created:         Thu Apr 11 16:29:34 CEST 2019

xlr {
  template('Release Blueprints') {
    folder('Shared')
    variables {
      stringVariable('BLUEPRINTS_VERSION') {
        label 'Blueprints Version'
      }
    }
    scheduledStartDate Date.parse("yyyy-MM-dd'T'HH:mm:ssZ", '2019-04-08T09:00:00+0200')
    phases {
      phase('Verification') {
        tasks {
          script('Verify Travis CI last build') {
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
print("\\nLast build #: " + repo['last_build_number'])
print("\\nLast build status: " + repo['last_build_state'])
if repo['last_build_state'] != "passed":
  print("\\nCheck the logs on: https://travis-ci.org/xebialabs/blueprints/builds/" + repo['last_build_id'])
  exit(1)
'''])
          }
        }
      }
      phase('Github') {
        color '#0099CC'
        tasks {
          custom('Create Branch') {
            script {
              type 'github.CreateBranch'
              server 'XebiaLabs GitHub (from community GitHub plugin)'
              organization 'xebialabs'
              repositoryName 'blueprints'
              newBranch '${BLUEPRINTS_VERSION}-maintenance'
            }
          }
          custom('Create PR development -> master') {
            script {
              type 'github.CreatePullRequest'
              server 'XebiaLabs GitHub (from community GitHub plugin)'
            }
          }
          custom('Merge PR development -> master') {
            script {
              type 'github.MergePullRequest'
              server 'XebiaLabs GitHub (from community GitHub plugin)'
            }
          }
        }
      }
      phase('Dist') {
        color '#0099CC'
      }
    }
    
  }
}