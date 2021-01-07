import groovy.json.JsonSlurper
import groovy.json.JsonOutput

def jsonSlurper = new JsonSlurper()
def json = jsonSlurper.parseText(new String(payload))

json.msg = json.msg.toUpperCase()

JsonOutput.toJson(json)