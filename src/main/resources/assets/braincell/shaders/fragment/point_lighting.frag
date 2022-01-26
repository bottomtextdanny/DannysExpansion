#version 430 compatibility
#extension GL_ARB_compute_shader: enable
#extension GL_ARB_shader_storage_buffer_object: enable
#extension GL_ARB_compute_variable_group_size: enable

struct Light {
    vec3 color;
    vec2 brightness_lightup;
    vec4 position_rad;
};

layout (std430, binding = 0) readonly buffer outputInfo {
    int regionalLightCount[&g_square];
    int lightDirectories[&g_square][&region_lights];
};

layout(rgba32f, binding = 1) readonly uniform sampler2D diffuse;
layout(rgba32f, binding = 2) readonly uniform sampler2D depth;
layout(rgba32f, binding = 3) readonly uniform sampler2D debug;
uniform float fog_start;
uniform float fog_end;
uniform vec4 fog_color;
uniform mat4 inv_model_view;
uniform Light lights[&max_lights];

in vec2 coords;
out vec4 fragColor;

vec4 linear_fog(vec4 inColor, float dist, float fogStart, float fogEnd, vec4 fogColor) {
    if (dist <= fogStart) {
        return inColor;
    }

    float fogValue = dist < fogEnd ? smoothstep(fogStart, fogEnd, dist) : 1.0;
    return vec4(mix(inColor.rgb, vec3(0.0), fogValue * fogColor.a), inColor.a);
}

vec3 getFragPos(float depthValue) {
    vec4 fragRelPos = vec4(coords.xy * 2.0F - 1.0F, depthValue * 2.0F - 1.0F, 1.0F) * inv_model_view;
    fragRelPos.xyz /= fragRelPos.w;

    return fragRelPos.xyz;
}

void main() {
    int x_tile = int(coords.x * &xgrid);
    int y_tile = int(coords.y * &ygrid);
    int flat_id = x_tile * &ygrid + y_tile;
    int regionLightCount = regionalLightCount[flat_id];

    vec4 color = vec4(0.0);
    vec4 dif;

    dif = texture(diffuse, coords.xy);

    &debug

    vec3 fragPos = getFragPos(texture(depth, coords.xy).r);

    for (int i = 0; i < regionLightCount; i++) {
        int lightInx = lightDirectories[flat_id][i];


        Light light = lights[lightInx];


        float dist = distance(fragPos, light.position_rad.xyz);
        if (dist < light.position_rad.w) {
            vec3 unpackedColor = light.color;
            float str = 1 - (dist / light.position_rad.w);
            float localAlpha = str * str;
            color.xyz += dif.xyz * localAlpha * light.brightness_lightup.y + unpackedColor * localAlpha * light.brightness_lightup.x;
            color.w = min(max(color.w, localAlpha), dif.w);

        }

    }


    fragColor = dif + linear_fog(color, distance(vec3(0.0), fragPos), fog_start, fog_end, fog_color);
}