#!/usr/bin/env bash
NAME=$(gh repo view --json name -q '.name')
OWNER=$(gh repo view --json owner -q '.owner.login')

sed -i "s/HytalePluginTemplate/$NAME/g" README.md
sed -i "s/HytalePluginTemplate/$NAME/g" settings.gradle
sed -i "s/HytalePluginTemplate/$NAME/g" src/main/java/io/profidev/HytalePluginTemplate/HytalePluginTemplate.java

mkdir -p src/main/java/io/profidev/$NAME
mv src/main/java/io/profidev/HytalePluginTemplate/HytalePluginTemplate.java src/main/java/io/profidev/$NAME/$NAME.java
rm -rf src/main/java/io/profidev/HytalePluginTemplate

rm setup.sh
mv ruleset.json tmp-ruleset.json

git add -A
git commit -m "refactor: rename files to plugin name"
git push

gh repo edit --enable-auto-merge --delete-branch-on-merge
gh api --method POST /repos/$OWNER/$NAME/rulesets --input tmp-ruleset.json | cat
rm tmp-ruleset.json
