# ibcore-executor-model

Each version of this component will produce 

1. An injectable `IBVersionsSupplier` instance that will detail the api version available
1. A set of POJOs off the jsonschema that is also available within this repo.  The copy 
of the JSONSchema is available in the resource path under `schema/v${apiversion}`.

Each subsequent version needs to build NEW versions (although that model hasn't really 
changed in about 8 years, so it prolly won't).