import json
import sys

WORKSPACE = sys.argv[1]
REPORT_FILE = 'build/reports/detekt/merge.sarif'

print("- Workspace : " + WORKSPACE)
# Load Detekt's SARIF report file
f = open(REPORT_FILE)

# Parse the file to JSOn
data = json.load(f)
results = data['runs'][0]['results']
for i in results:
    for j in i["locations"]:
        uri = j["physicalLocation"]["artifactLocation"]["uri"]
        # Update the artifact location uri to a relative path
        j["physicalLocation"]["artifactLocation"]["uri"] = uri.replace(WORKSPACE, "")
f.close()

# Save the changes in the report file
with open(REPORT_FILE, "w") as outfile:
    json.dump(data, outfile)

print("- Successfully updated the report file to support relative uris")
