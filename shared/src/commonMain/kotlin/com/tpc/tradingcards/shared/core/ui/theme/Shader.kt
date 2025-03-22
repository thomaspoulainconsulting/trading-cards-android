package com.tpc.tradingcards.shared.core.ui.theme

import org.intellij.lang.annotations.Language

@Language("AGSL")
const val ShaderChromaticAberration = """
uniform shader composable;
uniform float amount;

half4 main(float2 fragCoord) {
    
    half4 color = composable.eval(fragCoord);
    color.rgb = half3(
        composable.eval(float2(fragCoord.x - amount, fragCoord.y)).r,
        color.g,
        composable.eval(float2(fragCoord.x + amount, fragCoord.y)).b
    );
    return color;
}
"""