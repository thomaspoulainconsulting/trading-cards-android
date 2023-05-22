package com.tpc.tradingcards.core.ui.theme

const val ShaderChromaticAberration = """
uniform shader composable;
uniform float2 size;
uniform float amount;

half4 main(float2 fragCoord) {
    float distance = length(fragCoord - size * 0.5);
    //float displacement = distance / max(size.x, size.y) * amount;
    float displacement = pow(distance / max(size.x, size.y), 3.0) * amount;
    
    half4 color = composable.eval(fragCoord);
    color.rgb = half3(
        composable.eval(fragCoord - displacement).r,
        color.g,
        composable.eval(fragCoord + displacement).b
    );
    color.rgb *= color.a;
    return color;
}
"""