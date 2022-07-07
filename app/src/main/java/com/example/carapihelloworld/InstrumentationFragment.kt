package com.example.carapihelloworld

import android.content.res.Configuration
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension
import com.example.carapihelloworld.ui.NavistarTheme
import com.example.carapihelloworld.ui.OccDarkBlue

class InstrumentationFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_instrumentation, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val instrumentationComposeView = getView()?.findViewById<ComposeView>(R.id.instrumentation_compose_view)
        instrumentationComposeView?.setContent {
            NavistarTheme{
                MainComposable()
            }
        }
    }

    @Preview(showBackground = true, name="Main Composable")
    @Composable
    fun MainComposable(){
        val constraints = ConstraintSet{

            val bgImg = createRefFor("bgImg")
            val tirePressure = createRefFor("tirePressure")
            val gear = createRefFor("gear")

            val guidelineQuarterTop = createGuidelineFromTop(0.07f)
            val guidelineEighthStart = createGuidelineFromStart(0.125f)

            constrain(bgImg){
                top.linkTo(parent.top)
                end.linkTo(parent.end)
                bottom.linkTo(parent.bottom)
                start.linkTo(parent.start)
                width = Dimension.fillToConstraints
                height = Dimension.fillToConstraints
            }

            constrain(tirePressure){
                top.linkTo(guidelineQuarterTop)
                start.linkTo(guidelineEighthStart)
                width = Dimension.value(200.dp)
                height = Dimension.value(50.dp)
            }

            constrain(gear){

            }
        }

        ConstraintLayout(constraints,
            Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.horizontalGradient(
                        colors = listOf(
                            Color.White,
                            Color(android.graphics.Color.parseColor("#eef2f3"))
                        )
                    )
                )){

            Image(painter = painterResource(
                R.drawable.truck_chassis_low_tire_transparent),
                "LT Instrument Panel",
            modifier = Modifier.layoutId("bgImg"))

            NeonButton("Tire pressure:\n" +
                    "2nd tire 18 PSI (LOW)",
            "tirePressure")

//            NeonButton("Tire pressure:\n" +
//                    "2nd tire 18 PSI (LOW)",
//                "tirePressure")
        }
    }

    @Preview(showBackground = true,
        widthDp = 320,
        uiMode = Configuration.UI_MODE_NIGHT_YES,
        name = "Default Dark Preview")
    @Preview(showBackground = true, name = "Default Preview", widthDp = 320)
    @Composable
    private fun DefaultPreview(){
        NavistarTheme{
            MainComposable()
        }
    }

    @Preview(showBackground = true, name = "Button")
    @Composable
    private fun NeonButton(buttonText: String = "Tire pressure", layoutId: String = "tirePressure"){
        Box(contentAlignment = Alignment.Center,
            modifier = Modifier.layoutId(layoutId)){
            Image(painter = painterResource(id = R.drawable.neon_ruff_rect),
                "Tire pressure",
            modifier = Modifier.fillMaxWidth(),
            contentScale = ContentScale.FillBounds)
            Text(buttonText,
                fontSize = 15.sp,
                color = OccDarkBlue,
                textAlign = TextAlign.Center,
                fontFamily = FontFamily.SansSerif
            )
        }
    }
}