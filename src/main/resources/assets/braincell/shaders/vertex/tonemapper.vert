#version 110 

attribute vec4 Position;

uniform vec2 screen;
uniform mat4 proj_mat;

varying vec2 texCoord;

void main() {
    vec4 outPos = proj_mat * vec4(Position.xy, 0.0, 1.0);
    gl_Position = vec4(outPos.xy, 0.0, 1.0);
    texCoord = Position.xy / screen;

}