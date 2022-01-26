#version 110

uniform sampler2D diffuse;
uniform vec3 exponent;
uniform float desaturator;

varying vec2 texCoord;

float lerp(float pct, float start, float end) {
    return start + pct * (end - start);
}

void main() {
    vec4 dif = texture2D(diffuse, vec2(texCoord.x, 1.0 - texCoord.y));

    if (desaturator != 0.0) {
        float gray = (dif.r + dif.g + dif.b) / 3.0;
        dif =
        vec4(
        lerp(desaturator, dif.r, gray),
        lerp(desaturator, dif.g, gray),
        lerp(desaturator, dif.b, gray)
        , dif.a);
    }

    gl_FragColor = vec4(dif.x + exponent.x * dif.x, dif.y + exponent.y * dif.y, dif.z + exponent.z * dif.z, 1);
}