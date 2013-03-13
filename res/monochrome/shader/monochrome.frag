uniform sampler2D tex;

void main() {
	vec4 FragColor = texture2D(tex, gl_TexCoord[0].xy);
	float luma = dot(FragColor.rgb, vec3(0.299, 0.587, 0.114));
	gl_FragColor = vec4(luma, luma, luma, FragColor.a);
}
