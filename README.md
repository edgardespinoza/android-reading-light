# Reading light
this app help us to take the read of light and calculate the price to paid

- we use mvvm 
to 
- install ```./gradlew clean installRelease```

for deploy to appstore we create bundle

```
export KEY_ALIAS=""
export KEY_PASSWORD=""
export STORE_PASSWORD=""
export KEYSTORE_PATH=""

./gradlew bundleRelease
```
