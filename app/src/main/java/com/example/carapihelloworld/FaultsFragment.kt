package com.example.carapihelloworld

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.carapihelloworld.data.Fault
import com.example.carapihelloworld.data.Question
import com.example.carapihelloworld.ui.NavistarTheme
import com.example.carapihelloworld.ui.*

class FaultsFragment : Fragment() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val faultsComposeView = getView()?.findViewById<ComposeView>(R.id.faults_compose_view)
        faultsComposeView?.setContent {
            NavistarTheme{
                MainComposable()
            }
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_faults, container, false)
    }

    @Composable
    private fun Greeting(fault: Fault){
        Card(backgroundColor = Color.White,
        modifier = Modifier.padding(vertical = 5.dp, horizontal = 16.dp)){
            CardContent(fault = fault)
        }
    }

    @Composable
    private fun CardContent(fault: Fault) {
        var expanded by rememberSaveable { mutableStateOf(false) }

        Row(modifier = Modifier
            .padding(12.dp)
            .animateContentSize(
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessLow
                )
            )
        ){
            Column(modifier = Modifier
                .weight(1f)
                .padding(12.dp)){
                Row(verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(bottom = 10.dp)) {
                    Image(
                        painter = painterResource(fault.image),
                        contentDescription = fault.status,

                        modifier = Modifier
                            .padding(end = 10.dp)
                            .height(30.dp)
                            .width(30.dp)
                    )
                    Text(
                        text = fault.text,
                        style = MaterialTheme.typography.h6.copy(
                            fontWeight = FontWeight.ExtraBold
                        )
                    )
                }

                Text(text = fault.status,
                modifier = Modifier.padding(start = 42.dp))
                Text(text = "${fault.fCAP}",
                    color = MaterialTheme.colors.primary,
                    style = MaterialTheme.typography.body1
                    .copy(
                        fontWeight = FontWeight.Bold
                    ),
                    modifier = Modifier.padding(start = 42.dp))
                Text(text = "Count: ${fault.count}",
                    modifier = Modifier.padding(start = 42.dp))

                if(expanded){
                    Column(modifier = Modifier.padding(top = 16.dp)) {
                        for(question in fault.questions){
                            Question(question)
                        }
                        OutlinedButton(onClick = { /*TODO*/ },

                        modifier = Modifier.padding(start = 42.dp)) {
                            Text(text = "Request Service")
                        }
                    }
                }
            }

            IconButton(onClick = { expanded = !expanded}){
                Icon(
                    imageVector = if(expanded) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
                    contentDescription = if(expanded) {
                        stringResource(id = R.string.ShowLess)
                    } else {
                        stringResource(id = R.string.ShowMore)
                    }
                )
            }
        }
    }

    @Preview(showBackground = true, name="Main Composable")
    @Composable
    fun MainComposable(){
       Greetings()
    }

    @Composable
    private fun Greetings(){
        val faults = getFaults()
        Surface(color = MaterialTheme.colors.background){
            LazyColumn(modifier = Modifier.padding(vertical = 4.dp)) {
                items(items = faults){fault ->
                    Greeting(fault = fault)
                }
            }
        }
    }

    @Preview(showBackground = true,
    widthDp = 320,
    uiMode = UI_MODE_NIGHT_YES,
    name = "Default Dark Preview")
    @Preview(showBackground = true, name = "Default Preview", widthDp = 320)
    @Composable
    fun DefaultPreview(){
        NavistarTheme{
            MainComposable()
        }
    }

    @Preview(showBackground = true,
    widthDp = 320,
    name ="Question")
    @Composable
    private fun Question(question: Question = Question("Whats 1 + 1?")){
        Text(question.question,
        modifier = Modifier.padding(start = 42.dp, top = 5.dp))
        SimpleRadioGroupComponent(question.answers)
    }

    @Composable
    fun SimpleRadioGroupComponent(answers: List<String>) {
        val (selectedOption, onOptionSelected) = remember { mutableStateOf(answers.last()) }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(start = 42.dp, bottom = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            for(answer in answers){
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    RadioButton(
                        selected = answer == selectedOption,
                        onClick = { onOptionSelected(answer)},
                        colors = RadioButtonDefaults.colors(
                            selectedColor = OccDarkBlue,
                            unselectedColor = OccBlue
                        ))
                    Text(answer)
                }
            }
        }
    }

    private fun getFaults() : ArrayList<Fault> {
        val faults = arrayListOf(
            Fault(R.drawable.service_immediatly,
                "After treatment 1 Diesel Particulate Filter Differential Pressure : Data Valid But A",
                "0-SPN3251-15",
                "Previously Active",
                8,
                listOf(
                    Question("Is the DPF Lamp Illuminated?"),
                    Question("Is the DPF Lamp Flashing?"),
                    Question("Can the vehicle complete a parked regen?"),
                    Question("Has your vehicle requested or completed frequent regens?"),
                    Question("Has there been any change in the Engine Performance like Low Power?"),
                    Question("Are you urgently concerned about addressing this scheduled maintenance at this time (your judgement call)?"),
                    Question("Are you urgently concerned about addressing this fuel economy issue at this time (your judgement call)?")
                )),
            Fault(R.drawable.service_soon,
                "Engine Fuel 1 Injector Metering Rail 1 Cranking Pressure : Data Valid But Below Normal Operational Range - Most Severe Level",
                "0-SPN5585-1",
                "Previously Active",
                1,
                listOf(
                    Question("Is the MIL Lamp Illuminated?"),
                    Question("Is fuel leaking from a fuel line?"),
                    Question("Is there fuel leaking from the engine compartment?"),
                    Question("Is the vehicles fuel level low?"),
                    Question("Is the fuel system contaminated with water, DEF, or debris?")
                )
            ),
            Fault(R.drawable.service_soon,
                "Predictive Cruise Control Fault",
                "17-SPN520193-12",
                "Previously Active",
                2,
                listOf(Question("Is the cruise control inoperative?"))),
            Fault(R.drawable.service_soon,
                "Predictive Cruise Control GPS Not Active",
                "17-SPN520204-7",
                "Previously Active",
                1,
                listOf(
                    Question(" Is the cruise control inoperative?")
                )),
            Fault(R.drawable.service_soon,
                "FLC20 CCD Input Failure",
                "209-SPN1564-2",
                "Previously Active",
                2,
                listOf(
                    Question("Is there an alert for Adaptive Cruise Control (ACC) / Collision Mitigation System (CMS) system alert in gauge cluster?"),
                    Question("Are you urgently concerned about addressing this safety issue at this time (your judgement call)?")
                ))
        )

        return faults
    }
}