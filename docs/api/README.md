# OpenAPI

This is the OpenAPI documentation for interface of the server. It is written using
[OpenAPI 3.0.3](https://spec.openapis.org/oas/v3.0.3) and is used to generate client code for the server and model
classes for the server.

The server supports JSON as well as ProtoBuf as a serialization format. The OpenAPI documentation is written in a way
that it can be used to generate client code for both formats.

Preferred way to interact with the server is to use the [python sdk](../python-sdk) that is provided in this repository.
If you have to interact with the server from a different language, you can use the OpenAPI documentation to generate
client code for your language. Try to use ProtoBuf as a serialization format if possible as it is much more efficient
than JSON.

## Rendering

To render the api simply use:

```bash
npx @redocly/cli build-docs openapi.yaml
```

This yields a `redoc-static.html` file that can be used to render the documentation.

To render the preview simply execute [python server script](server.py) and follow the instructions by navigating to
`http://localhost:8000`.
