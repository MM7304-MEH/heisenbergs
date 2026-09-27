package com.cuewise.app

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.automirrored.rounded.ArrowForward
import androidx.compose.material.icons.rounded.BarChart
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.ChevronRight
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material.icons.rounded.Lock
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material.icons.rounded.Refresh
import androidx.compose.material.icons.rounded.SelfImprovement
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material.icons.rounded.Timer
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cuewise.app.ui.theme.Coral
import com.cuewise.app.ui.theme.CoralSoft
import com.cuewise.app.ui.theme.CuewiseTheme
import com.cuewise.app.ui.theme.Ink
import com.cuewise.app.ui.theme.Lavender
import com.cuewise.app.ui.theme.LavenderDeep
import com.cuewise.app.ui.theme.Line
import com.cuewise.app.ui.theme.Midnight
import com.cuewise.app.ui.theme.Mint
import com.cuewise.app.ui.theme.MintDeep
import com.cuewise.app.ui.theme.Muted
import com.cuewise.app.ui.theme.Paper
import androidx.compose.foundation.text.KeyboardOptions

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CuewiseTheme {
                CuewiseApp()
            }
        }
    }
}

private enum class Destination {
    HOME, PROGRESS, PROFILE, SCENARIO, REHEARSAL, FEEDBACK, PAYWALL
}

private data class Scenario(
    val id: String,
    val title: String,
    val category: String,
    val emoji: String,
    val duration: String,
    val context: String,
    val goal: String,
    val prompt: String,
    val isPremium: Boolean,
    val tint: Color
)

private val scenarios = listOf(
    Scenario(
        id = "ask-help",
        title = "Ask for help without sounding unprepared",
        category = "Confidence",
        emoji = "🧭",
        duration = "3 min",
        context = "You are stuck on an assignment or task and want guidance without making the other person feel like you skipped the work.",
        goal = "Show what you tried, name the exact gap, and make the next step easy.",
        prompt = "Your professor or teammate asks: ‘What part are you stuck on?’ What do you say?",
        isPremium = false,
        tint = Mint
    ),
    Scenario(
        id = "boundary",
        title = "Set a boundary without making it awkward",
        category = "Boundaries",
        emoji = "🫶",
        duration = "4 min",
        context = "A teammate keeps sending last-minute work late at night and expects an immediate response.",
        goal = "Be warm and direct: name the pattern, your limit, and a workable alternative.",
        prompt = "Your teammate messages at 11:45 PM: ‘Can you finish this tonight?’ How do you respond?",
        isPremium = false,
        tint = Lavender
    ),
    Scenario(
        id = "interview",
        title = "Answer ‘Tell me about yourself’ with a point",
        category = "Career",
        emoji = "✨",
        duration = "5 min",
        context = "You have an interview for an internship or first job. The interviewer wants a clear story, not your entire biography.",
        goal = "Connect where you are, what you have practised, and why this opportunity is the next useful step.",
        prompt = "The interviewer smiles and says: ‘Tell me about yourself.’ What do you say?",
        isPremium = true,
        tint = CoralSoft
    ),
    Scenario(
        id = "feedback",
        title = "Give feedback that can actually be heard",
        category = "Leadership",
        emoji = "🌱",
        duration = "5 min",
        context = "A group-project partner is enthusiastic but regularly misses agreed deadlines.",
        goal = "Protect the relationship while making the impact and next agreement unmistakable.",
        prompt = "Your partner asks: ‘How do you think the project is going?’ What do you say?",
        isPremium = true,
        tint = Color(0xFFFFE0B5)
    )
)

private data class Feedback(
    val score: Int,
    val headline: String,
    val strengths: List<String>,
    val upgrade: String,
    val nextCue: String
)

@Composable
fun CuewiseApp() {
    var destination by rememberSaveable { mutableStateOf(Destination.HOME) }
    var selectedId by rememberSaveable { mutableStateOf(scenarios.first().id) }
    var response by rememberSaveable { mutableStateOf("") }
    var completedRehearsals by rememberSaveable { mutableIntStateOf(3) }
    var confidence by rememberSaveable { mutableIntStateOf(68) }
    var isPro by rememberSaveable { mutableStateOf(false) }

    val selected = scenarios.firstOrNull { it.id == selectedId } ?: scenarios.first()
    val showNavigation = destination == Destination.HOME ||
        destination == Destination.PROGRESS || destination == Destination.PROFILE

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        bottomBar = {
            if (showNavigation) {
                CuewiseBottomBar(destination = destination, onDestination = { destination = it })
            }
        }
    ) { padding ->
        Box(modifier = Modifier.fillMaxSize().padding(padding)) {
            when (destination) {
                Destination.HOME -> HomeScreen(
                    completedRehearsals = completedRehearsals,
                    confidence = confidence,
                    isPro = isPro,
                    onScenario = { scenario ->
                        selectedId = scenario.id
                        destination = if (scenario.isPremium && !isPro) {
                            Destination.PAYWALL
                        } else {
                            Destination.SCENARIO
                        }
                    },
                    onQuickStart = {
                        selectedId = scenarios.first().id
                        destination = Destination.SCENARIO
                    },
                    modifier = Modifier.fillMaxSize()
                )

                Destination.PROGRESS -> ProgressScreen(
                    completedRehearsals = completedRehearsals,
                    confidence = confidence,
                    onBack = { destination = Destination.HOME },
                    modifier = Modifier.fillMaxSize()
                )

                Destination.PROFILE -> ProfileScreen(
                    onBack = { destination = Destination.HOME },
                    onRestore = {
                        RevenueCatManager.restore(
                            onSuccess = { info ->
                                isPro = info.entitlements[RevenueCatManager.PRO_ENTITLEMENT]?.isActive == true
                            },
                            onError = { }
                        )
                    },
                    modifier = Modifier.fillMaxSize()
                )

                Destination.SCENARIO -> ScenarioScreen(
                    scenario = selected,
                    onBack = { destination = Destination.HOME },
                    onStart = {
                        response = ""
                        destination = Destination.REHEARSAL
                    },
                    modifier = Modifier.fillMaxSize()
                )

                Destination.REHEARSAL -> RehearsalScreen(
                    scenario = selected,
                    response = response,
                    onResponseChange = { response = it },
                    onBack = { destination = Destination.SCENARIO },
                    onSubmit = {
                        completedRehearsals += 1
                        confidence = (confidence + 2).coerceAtMost(99)
                        destination = Destination.FEEDBACK
                    },
                    modifier = Modifier.fillMaxSize()
                )

                Destination.FEEDBACK -> FeedbackScreen(
                    scenario = selected,
                    response = response,
                    onBack = { destination = Destination.REHEARSAL },
                    onTryAgain = { destination = Destination.REHEARSAL },
                    onDone = { destination = Destination.HOME },
                    modifier = Modifier.fillMaxSize()
                )

                Destination.PAYWALL -> PaywallScreen(
                    scenario = selected,
                    isDemoMode = !RevenueCatManager.isConfigured(),
                    onBack = { destination = Destination.HOME },
                    onUnlocked = {
                        isPro = true
                        destination = Destination.SCENARIO
                    },
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}

@Composable
private fun HomeScreen(
    completedRehearsals: Int,
    confidence: Int,
    isPro: Boolean,
    onScenario: (Scenario) -> Unit,
    onQuickStart: () -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .background(MaterialTheme.colorScheme.background)
            .statusBarsPadding(),
        contentPadding = PaddingValues(start = 20.dp, end = 20.dp, top = 18.dp, bottom = 28.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier.size(38.dp).clip(RoundedCornerShape(14.dp)).background(Midnight),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("⌁", color = Coral, fontSize = 25.sp, fontWeight = FontWeight.Bold)
                    }
                    Spacer(Modifier.width(10.dp))
                    Column {
                        Text("cuewise", style = MaterialTheme.typography.titleLarge, color = Ink)
                        Text("your next clear sentence", style = MaterialTheme.typography.labelSmall, color = Muted)
                    }
                }
                Box(
                    modifier = Modifier.size(42.dp).clip(CircleShape).background(Lavender),
                    contentAlignment = Alignment.Center
                ) {
                    Text("A", color = LavenderDeep, fontWeight = FontWeight.Bold)
                }
            }
        }

        item {
            HeroCard(
                completedRehearsals = completedRehearsals,
                confidence = confidence,
                onQuickStart = onQuickStart
            )
        }

        item {
            SectionHeading(title = "Choose a moment", action = "See all")
        }

        item {
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(end = 12.dp)
            ) {
                items(scenarios) { scenario ->
                    ScenarioCard(scenario = scenario, isPro = isPro, onClick = { onScenario(scenario) })
                }
            }
        }

        item {
            SectionHeading(title = "Your week", action = null)
        }

        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                border = BorderStroke(1.dp, Line)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(18.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    MiniStat(icon = "↗", value = "$completedRehearsals", label = "rehearsals")
                    Divider(modifier = Modifier.height(42.dp).width(1.dp), color = Line)
                    MiniStat(icon = "✦", value = "$confidence%", label = "confidence")
                    Divider(modifier = Modifier.height(42.dp).width(1.dp), color = Line)
                    MiniStat(icon = "◷", value = "7m", label = "saved")
                }
            }
        }

        item {
            Text(
                text = "Small reps become calm moments.",
                modifier = Modifier.fillMaxWidth().padding(top = 4.dp),
                textAlign = TextAlign.Center,
                color = Muted,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Composable
private fun HeroCard(
    completedRehearsals: Int,
    confidence: Int,
    onQuickStart: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth().shadow(8.dp, RoundedCornerShape(28.dp)),
        shape = RoundedCornerShape(28.dp),
        colors = CardDefaults.cardColors(containerColor = Midnight)
    ) {
        Box(
            modifier = Modifier.fillMaxWidth().background(
                Brush.linearGradient(listOf(Midnight, Color(0xFF1B2948), Color(0xFF34264A)))
            )
        ) {
            Column(modifier = Modifier.padding(22.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Top
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text("GOOD TO SEE YOU, A.", color = Mint, style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold)
                        Spacer(Modifier.height(10.dp))
                        Text(
                            "What conversation\nneeds a little practice?",
                            color = Color.White,
                            style = MaterialTheme.typography.headlineMedium
                        )
                    }
                    Box(
                        modifier = Modifier.size(64.dp).clip(CircleShape).background(Coral.copy(alpha = 0.18f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("✦", color = Coral, fontSize = 34.sp)
                    }
                }
                Spacer(Modifier.height(20.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    ProgressPill(label = "$completedRehearsals reps", value = "this week")
                    Spacer(Modifier.width(8.dp))
                    ProgressPill(label = "$confidence%", value = "confidence")
                }
                Spacer(Modifier.height(20.dp))
                Button(
                    onClick = onQuickStart,
                    shape = RoundedCornerShape(15.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.White, contentColor = Midnight),
                    contentPadding = PaddingValues(horizontal = 17.dp, vertical = 13.dp)
                ) {
                    Text("Start a 3-min rehearsal", fontWeight = FontWeight.Bold)
                    Spacer(Modifier.width(8.dp))
                    Icon(Icons.AutoMirrored.Rounded.ArrowForward, contentDescription = null, modifier = Modifier.size(18.dp))
                }
            }
        }
    }
}

@Composable
private fun ProgressPill(label: String, value: String) {
    Column(
        modifier = Modifier.clip(RoundedCornerShape(13.dp)).background(Color.White.copy(alpha = 0.10f)).padding(horizontal = 12.dp, vertical = 8.dp)
    ) {
        Text(label, color = Color.White, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.labelLarge)
        Text(value, color = Color.White.copy(alpha = 0.62f), style = MaterialTheme.typography.labelSmall)
    }
}

@Composable
private fun ScenarioCard(scenario: Scenario, isPro: Boolean, onClick: () -> Unit) {
    Card(
        modifier = Modifier.width(250.dp),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = scenario.tint.copy(alpha = 0.55f)),
        onClick = onClick
    ) {
        Column(modifier = Modifier.padding(18.dp), verticalArrangement = Arrangement.spacedBy(11.dp)) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text(scenario.emoji, fontSize = 26.sp)
                if (scenario.isPremium && !isPro) {
                    Icon(Icons.Rounded.Lock, contentDescription = "Premium", tint = Ink.copy(alpha = 0.58f), modifier = Modifier.size(17.dp))
                } else {
                    Text(scenario.duration, color = Ink.copy(alpha = 0.60f), style = MaterialTheme.typography.labelSmall)
                }
            }
            Text(scenario.category.uppercase(), color = Ink.copy(alpha = 0.62f), style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold)
            Text(scenario.title, color = Ink, style = MaterialTheme.typography.titleMedium, minLines = 2)
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("Practice", color = Ink.copy(alpha = 0.68f), style = MaterialTheme.typography.labelLarge)
                Spacer(Modifier.width(4.dp))
                Icon(Icons.AutoMirrored.Rounded.ArrowForward, contentDescription = null, tint = Ink.copy(alpha = 0.68f), modifier = Modifier.size(16.dp))
            }
        }
    }
}

@Composable
private fun SectionHeading(title: String, action: String?) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
        Text(title, style = MaterialTheme.typography.titleLarge, color = Ink)
        if (action != null) {
            Text(action, color = Coral, style = MaterialTheme.typography.labelLarge)
        }
    }
}

@Composable
private fun MiniStat(icon: String, value: String, label: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(icon, color = Coral, fontSize = 18.sp, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(3.dp))
        Text(value, color = Ink, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleMedium)
        Text(label, color = Muted, style = MaterialTheme.typography.labelSmall)
    }
}

@Composable
private fun CuewiseBottomBar(destination: Destination, onDestination: (Destination) -> Unit) {
    NavigationBar(
        containerColor = Color.White,
        tonalElevation = 0.dp,
        modifier = Modifier.navigationBarsPadding()
    ) {
        BottomItem("Today", Icons.Rounded.Home, destination == Destination.HOME) { onDestination(Destination.HOME) }
        BottomItem("Progress", Icons.Rounded.BarChart, destination == Destination.PROGRESS) { onDestination(Destination.PROGRESS) }
        BottomItem("Profile", Icons.Rounded.Person, destination == Destination.PROFILE) { onDestination(Destination.PROFILE) }
    }
}

@Composable
private fun BottomItem(label: String, icon: androidx.compose.ui.graphics.vector.ImageVector, selected: Boolean, onClick: () -> Unit) {
    NavigationBarItem(
        selected = selected,
        onClick = onClick,
        icon = { Icon(icon, contentDescription = label) },
        label = { Text(label) },
        colors = NavigationBarItemDefaults.colors(
            selectedIconColor = Coral,
            selectedTextColor = Coral,
            indicatorColor = CoralSoft,
            unselectedIconColor = Muted,
            unselectedTextColor = Muted
        )
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ScenarioScreen(
    scenario: Scenario,
    onBack: () -> Unit,
    onStart: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.background(MaterialTheme.colorScheme.background).statusBarsPadding()) {
        TopAppBar(
            title = { Text("Before the moment", color = Ink, fontWeight = FontWeight.Bold) },
            navigationIcon = { IconButton(onClick = onBack) { Icon(Icons.AutoMirrored.Rounded.ArrowBack, contentDescription = "Back", tint = Ink) } },
            colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.background)
        )
        Column(
            modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(horizontal = 20.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(18.dp)
        ) {
            Box(
                modifier = Modifier.size(70.dp).clip(RoundedCornerShape(22.dp)).background(scenario.tint),
                contentAlignment = Alignment.Center
            ) { Text(scenario.emoji, fontSize = 34.sp) }
            Text(scenario.category.uppercase(), color = Coral, style = MaterialTheme.typography.labelLarge, fontWeight = FontWeight.Bold)
            Text(scenario.title, color = Ink, style = MaterialTheme.typography.displaySmall)
            Text(scenario.context, color = Muted, style = MaterialTheme.typography.bodyLarge)
            InfoCard(title = "Your goal", body = scenario.goal, icon = "◎")
            InfoCard(title = "How it works", body = "You get one prompt, one honest attempt, and one small upgrade. Keep it human.", icon = "✦")
            Spacer(Modifier.height(4.dp))
            PrimaryButton(text = "Start rehearsal", onClick = onStart, modifier = Modifier.fillMaxWidth())
            Text("Private by design · about ${scenario.duration}", color = Muted, style = MaterialTheme.typography.labelSmall, modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center)
            Spacer(Modifier.height(20.dp))
        }
    }
}

@Composable
private fun InfoCard(title: String, body: String, icon: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = BorderStroke(1.dp, Line)
    ) {
        Row(modifier = Modifier.padding(17.dp), verticalAlignment = Alignment.Top) {
            Text(icon, color = Coral, fontSize = 22.sp, modifier = Modifier.padding(top = 1.dp))
            Spacer(Modifier.width(12.dp))
            Column(verticalArrangement = Arrangement.spacedBy(5.dp)) {
                Text(title, color = Ink, style = MaterialTheme.typography.titleMedium)
                Text(body, color = Muted, style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun RehearsalScreen(
    scenario: Scenario,
    response: String,
    onResponseChange: (String) -> Unit,
    onBack: () -> Unit,
    onSubmit: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.background(MaterialTheme.colorScheme.background).statusBarsPadding().imePadding()) {
        TopAppBar(
            title = { Text("Rehearsal 01", color = Ink, fontWeight = FontWeight.Bold) },
            navigationIcon = { IconButton(onClick = onBack) { Icon(Icons.AutoMirrored.Rounded.ArrowBack, contentDescription = "Back", tint = Ink) } },
            actions = { Text("1 of 1", color = Muted, style = MaterialTheme.typography.labelMedium, modifier = Modifier.padding(end = 18.dp)) },
            colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.background)
        )
        Column(
            modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(horizontal = 20.dp, vertical = 10.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            LinearProgressIndicator(
                progress = { 0.52f },
                modifier = Modifier.fillMaxWidth().height(5.dp).clip(RoundedCornerShape(5.dp)),
                color = Coral,
                trackColor = CoralSoft
            )
            Text("THE MOMENT", color = Coral, style = MaterialTheme.typography.labelLarge, fontWeight = FontWeight.Bold)
            Text(scenario.prompt, color = Ink, style = MaterialTheme.typography.headlineMedium)
            Text("Write the way you would actually say it. Clear is better than perfect.", color = Muted, style = MaterialTheme.typography.bodyMedium)
            OutlinedTextField(
                value = response,
                onValueChange = onResponseChange,
                modifier = Modifier.fillMaxWidth().height(190.dp),
                placeholder = { Text("Start with the sentence you would say out loud…", color = Muted.copy(alpha = 0.7f)) },
                shape = RoundedCornerShape(20.dp),
                colors = androidx.compose.material3.OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Coral,
                    unfocusedBorderColor = Line,
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White
                ),
                keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Sentences, keyboardType = KeyboardType.Text)
            )
            Row(modifier = Modifier.horizontalScroll(rememberScrollState()), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                listOf("I noticed…", "I need…", "Could we…").forEach { chip ->
                    FilterChip(
                        selected = false,
                        onClick = { onResponseChange(if (response.isBlank()) "$chip " else "$response $chip ") },
                        label = { Text(chip) }
                    )
                }
            }
            Spacer(Modifier.height(2.dp))
            PrimaryButton(
                text = if (response.trim().length < 20) "Write a little more" else "See my coaching",
                onClick = onSubmit,
                enabled = response.trim().length >= 20,
                modifier = Modifier.fillMaxWidth()
            )
            Text("Your response stays on this device in this prototype.", color = Muted, style = MaterialTheme.typography.labelSmall, modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center)
            Spacer(Modifier.height(20.dp))
        }
    }
}

@Composable
private fun FeedbackScreen(
    scenario: Scenario,
    response: String,
    onBack: () -> Unit,
    onTryAgain: () -> Unit,
    onDone: () -> Unit,
    modifier: Modifier = Modifier
) {
    val feedback = remember(response, scenario.id) { evaluateResponse(response, scenario) }
    Column(modifier = modifier.background(MaterialTheme.colorScheme.background).statusBarsPadding()) {
        TopAppBar(
            title = { Text("Your coaching", color = Ink, fontWeight = FontWeight.Bold) },
            navigationIcon = { IconButton(onClick = onBack) { Icon(Icons.AutoMirrored.Rounded.ArrowBack, contentDescription = "Back", tint = Ink) } },
            colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.background)
        )
        Column(
            modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(horizontal = 20.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                ScoreCircle(score = feedback.score)
                Spacer(Modifier.width(16.dp))
                Column {
                    Text(feedback.headline, color = Ink, style = MaterialTheme.typography.titleLarge)
                    Spacer(Modifier.height(4.dp))
                    Text("One useful rep is better than a perfect draft.", color = Muted, style = MaterialTheme.typography.bodyMedium)
                }
            }
            Text("WHAT IS ALREADY WORKING", color = Coral, style = MaterialTheme.typography.labelLarge, fontWeight = FontWeight.Bold)
            feedback.strengths.forEach { strength ->
                FeedbackRow(text = strength, positive = true)
            }
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(22.dp),
                colors = CardDefaults.cardColors(containerColor = Midnight)
            ) {
                Column(modifier = Modifier.padding(19.dp), verticalArrangement = Arrangement.spacedBy(9.dp)) {
                    Text("ONE SMALL UPGRADE", color = Mint, style = MaterialTheme.typography.labelLarge, fontWeight = FontWeight.Bold)
                    Text(feedback.upgrade, color = Color.White, style = MaterialTheme.typography.titleMedium)
                    Text(feedback.nextCue, color = Color.White.copy(alpha = 0.68f), style = MaterialTheme.typography.bodyMedium)
                }
            }
            Text("Your attempt", color = Ink, style = MaterialTheme.typography.titleMedium)
            Text("“$response”", color = Muted, style = MaterialTheme.typography.bodyMedium)
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp), modifier = Modifier.fillMaxWidth()) {
                OutlinedButton(onClick = onTryAgain, modifier = Modifier.weight(1f), shape = RoundedCornerShape(15.dp)) {
                    Icon(Icons.Rounded.Refresh, contentDescription = null, modifier = Modifier.size(17.dp))
                    Spacer(Modifier.width(7.dp))
                    Text("Try again")
                }
                PrimaryButton(text = "I'm done", onClick = onDone, modifier = Modifier.weight(1f))
            }
            Spacer(Modifier.height(20.dp))
        }
    }
}

@Composable
private fun ScoreCircle(score: Int) {
    Box(modifier = Modifier.size(96.dp), contentAlignment = Alignment.Center) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            drawArc(
                color = Line,
                startAngle = -90f,
                sweepAngle = 360f,
                useCenter = false,
                style = Stroke(width = 10.dp.toPx(), cap = StrokeCap.Round)
            )
            drawArc(
                color = Coral,
                startAngle = -90f,
                sweepAngle = score / 100f * 360f,
                useCenter = false,
                style = Stroke(width = 10.dp.toPx(), cap = StrokeCap.Round)
            )
        }
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text("$score", color = Ink, fontWeight = FontWeight.Bold, fontSize = 25.sp)
            Text("/ 100", color = Muted, style = MaterialTheme.typography.labelSmall)
        }
    }
}

@Composable
private fun FeedbackRow(text: String, positive: Boolean) {
    Row(verticalAlignment = Alignment.Top, modifier = Modifier.fillMaxWidth()) {
        Box(
            modifier = Modifier.size(24.dp).clip(CircleShape).background(if (positive) Mint else CoralSoft),
            contentAlignment = Alignment.Center
        ) {
            Icon(Icons.Rounded.Check, contentDescription = null, tint = if (positive) MintDeep else Coral, modifier = Modifier.size(15.dp))
        }
        Spacer(Modifier.width(10.dp))
        Text(text, color = Ink, style = MaterialTheme.typography.bodyMedium, modifier = Modifier.weight(1f))
    }
}

@Composable
private fun ProgressScreen(
    completedRehearsals: Int,
    confidence: Int,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.background(MaterialTheme.colorScheme.background).statusBarsPadding()) {
        TopAppBar(
            title = { Text("Your progress", color = Ink, fontWeight = FontWeight.Bold) },
            navigationIcon = { IconButton(onClick = onBack) { Icon(Icons.AutoMirrored.Rounded.ArrowBack, contentDescription = "Back", tint = Ink) } },
            colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.background)
        )
        Column(modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(20.dp), verticalArrangement = Arrangement.spacedBy(17.dp)) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(26.dp),
                colors = CardDefaults.cardColors(containerColor = Midnight)
            ) {
                Column(modifier = Modifier.padding(22.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Text("A calmer version of you is built in reps.", color = Color.White, style = MaterialTheme.typography.headlineMedium)
                    Text("Keep one small promise to yourself this week: practise before you perform.", color = Color.White.copy(alpha = 0.68f), style = MaterialTheme.typography.bodyMedium)
                    Spacer(Modifier.height(2.dp))
                    Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                        ProgressPill(label = "$completedRehearsals", value = "rehearsals")
                        ProgressPill(label = "$confidence%", value = "confidence")
                    }
                }
            }
            Text("This week", color = Ink, style = MaterialTheme.typography.titleLarge)
            Card(modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(21.dp), colors = CardDefaults.cardColors(containerColor = Color.White), border = BorderStroke(1.dp, Line)) {
                Column(modifier = Modifier.padding(18.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
                    ProgressLine(label = "Practice consistency", value = 0.72f, caption = "3 of 4 days")
                    ProgressLine(label = "Second-attempt rate", value = 0.61f, caption = "getting braver")
                    ProgressLine(label = "Clarity", value = 0.78f, caption = "your strongest cue")
                }
            }
            InfoCard(title = "Your pattern", body = "You are strongest when you name the situation before you name the solution. Keep doing that.", icon = "✦")
        }
    }
}

@Composable
private fun ProgressLine(label: String, value: Float, caption: String) {
    Column(verticalArrangement = Arrangement.spacedBy(7.dp)) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(label, color = Ink, style = MaterialTheme.typography.titleMedium)
            Text(caption, color = Muted, style = MaterialTheme.typography.labelSmall)
        }
        LinearProgressIndicator(
            progress = { value },
            modifier = Modifier.fillMaxWidth().height(8.dp).clip(RoundedCornerShape(8.dp)),
            color = Coral,
            trackColor = CoralSoft
        )
    }
}

@Composable
private fun ProfileScreen(
    onBack: () -> Unit,
    onRestore: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.background(MaterialTheme.colorScheme.background).statusBarsPadding()) {
        TopAppBar(
            title = { Text("Profile", color = Ink, fontWeight = FontWeight.Bold) },
            navigationIcon = { IconButton(onClick = onBack) { Icon(Icons.AutoMirrored.Rounded.ArrowBack, contentDescription = "Back", tint = Ink) } },
            colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.background)
        )
        Column(modifier = Modifier.fillMaxSize().padding(horizontal = 20.dp), verticalArrangement = Arrangement.spacedBy(14.dp)) {
            Card(modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(24.dp), colors = CardDefaults.cardColors(containerColor = Color.White), border = BorderStroke(1.dp, Line)) {
                Row(modifier = Modifier.padding(19.dp), verticalAlignment = Alignment.CenterVertically) {
                    Box(modifier = Modifier.size(58.dp).clip(CircleShape).background(Lavender), contentAlignment = Alignment.Center) { Text("A", color = LavenderDeep, fontSize = 22.sp, fontWeight = FontWeight.Bold) }
                    Spacer(Modifier.width(13.dp))
                    Column {
                        Text("Your private practice space", color = Ink, style = MaterialTheme.typography.titleMedium)
                        Text("No performance. Just progress.", color = Muted, style = MaterialTheme.typography.bodyMedium)
                    }
                }
            }
            ProfileAction(icon = Icons.Rounded.Refresh, title = "Restore premium", subtitle = "Bring back a previous purchase", onClick = onRestore)
            ProfileAction(icon = Icons.Rounded.Info, title = "About Cuewise", subtitle = "A small tool for clearer moments", onClick = { })
            ProfileAction(icon = Icons.Rounded.Settings, title = "Privacy & settings", subtitle = "Your practice is yours", onClick = { })
            Spacer(Modifier.height(8.dp))
            Text("Cuewise 0.1 · built for thoughtful practice", color = Muted, style = MaterialTheme.typography.labelSmall, modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center)
        }
    }
}

@Composable
private fun ProfileAction(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    subtitle: String,
    onClick: () -> Unit
) {
    Card(onClick = onClick, modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(19.dp), colors = CardDefaults.cardColors(containerColor = Color.White), border = BorderStroke(1.dp, Line)) {
        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Box(modifier = Modifier.size(40.dp).clip(RoundedCornerShape(13.dp)).background(CoralSoft), contentAlignment = Alignment.Center) {
                Icon(icon, contentDescription = null, tint = Coral, modifier = Modifier.size(20.dp))
            }
            Spacer(Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(title, color = Ink, style = MaterialTheme.typography.titleMedium)
                Text(subtitle, color = Muted, style = MaterialTheme.typography.bodySmall)
            }
            Icon(Icons.Rounded.ChevronRight, contentDescription = null, tint = Muted)
        }
    }
}

@Composable
private fun PaywallScreen(
    scenario: Scenario,
    isDemoMode: Boolean,
    onBack: () -> Unit,
    onUnlocked: () -> Unit,
    modifier: Modifier = Modifier
) {
    var isLoading by remember { mutableStateOf(false) }
    var error by remember { mutableStateOf<String?>(null) }
    val contextActivity = androidx.compose.ui.platform.LocalContext.current as? Activity

    Column(modifier = modifier.background(MaterialTheme.colorScheme.background).statusBarsPadding()) {
        TopAppBar(
            title = { Text("More moments, when you need them", color = Ink, fontWeight = FontWeight.Bold) },
            navigationIcon = { IconButton(onClick = onBack) { Icon(Icons.Rounded.Close, contentDescription = "Close", tint = Ink) } },
            colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.background)
        )
        Column(modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(horizontal = 20.dp, vertical = 10.dp), verticalArrangement = Arrangement.spacedBy(17.dp)) {
            Box(modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(28.dp)).background(Brush.linearGradient(listOf(Midnight, Color(0xFF34264A)))).padding(24.dp)) {
                Column(verticalArrangement = Arrangement.spacedBy(13.dp)) {
                    Box(modifier = Modifier.size(54.dp).clip(CircleShape).background(Coral.copy(alpha = 0.18f)), contentAlignment = Alignment.Center) { Text("✦", color = Coral, fontSize = 27.sp) }
                    Text("The moments that\nneed a second take.", color = Color.White, style = MaterialTheme.typography.headlineMedium)
                    Text("Unlock deeper scenarios and keep your practice going when the stakes get higher.", color = Color.White.copy(alpha = 0.70f), style = MaterialTheme.typography.bodyMedium)
                }
            }
            Text("Cuewise Plus includes", color = Ink, style = MaterialTheme.typography.titleLarge)
            listOf("Interview and opportunity scenarios", "Leadership and difficult-feedback practice", "Unlimited second attempts and progress history").forEach { feature ->
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(modifier = Modifier.size(26.dp).clip(CircleShape).background(Mint), contentAlignment = Alignment.Center) { Icon(Icons.Rounded.Check, contentDescription = null, tint = MintDeep, modifier = Modifier.size(16.dp)) }
                    Spacer(Modifier.width(10.dp))
                    Text(feature, color = Ink, style = MaterialTheme.typography.bodyLarge)
                }
            }
            if (error != null) {
                Text(error.orEmpty(), color = Coral, style = MaterialTheme.typography.bodySmall)
            }
            PrimaryButton(
                text = when {
                    isLoading -> "Opening secure checkout…"
                    isDemoMode -> "Preview premium flow"
                    else -> "Start your free trial"
                },
                enabled = !isLoading,
                onClick = {
                    if (isDemoMode) {
                        onUnlocked()
                    } else if (contextActivity != null) {
                        isLoading = true
                        RevenueCatManager.loadPackages(
                            onSuccess = { packages ->
                                val first = packages.firstOrNull()
                                if (first == null) {
                                    isLoading = false
                                    error = "No offering is configured yet. Add a product in RevenueCat and try again."
                                } else {
                                    RevenueCatManager.purchase(
                                        activity = contextActivity,
                                        packageToBuy = first,
                                        onSuccess = { info ->
                                            isLoading = false
                                            if (info.entitlements[RevenueCatManager.PRO_ENTITLEMENT]?.isActive == true) onUnlocked()
                                            else error = "Purchase completed, but the premium entitlement is not active yet."
                                        },
                                        onError = { message ->
                                            isLoading = false
                                            error = message
                                        }
                                    )
                                }
                            },
                            onError = { message ->
                                isLoading = false
                                error = message
                            }
                        )
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )
            Text(
                if (isDemoMode) "Local demo mode is active. Connect a RevenueCat Test Store key to test the real purchase flow." else "Cancel anytime during the trial. Restore purchases from Profile.",
                color = Muted,
                style = MaterialTheme.typography.labelSmall,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
            Spacer(Modifier.height(20.dp))
        }
    }
}

@Composable
private fun PrimaryButton(text: String, onClick: () -> Unit, modifier: Modifier = Modifier, enabled: Boolean = true) {
    Button(
        onClick = onClick,
        enabled = enabled,
        modifier = modifier.height(54.dp),
        shape = RoundedCornerShape(16.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Coral, contentColor = Color.White, disabledContainerColor = Line, disabledContentColor = Muted)
    ) {
        Text(text, fontWeight = FontWeight.Bold)
        Spacer(Modifier.width(8.dp))
        Icon(Icons.AutoMirrored.Rounded.ArrowForward, contentDescription = null, modifier = Modifier.size(18.dp))
    }
}

private fun evaluateResponse(response: String, scenario: Scenario): Feedback {
    val lower = response.lowercase()
    val hasContext = listOf("because", "when", "about", "noticed", "impact", "situation").any { it in lower }
    val hasBoundary = listOf("need", "could we", "can we", "would it", "by", "available", "expect").any { it in lower }
    val hasEmpathy = listOf("understand", "appreciate", "know", "hear", "thanks", "sorry").any { it in lower }
    val hasNextStep = listOf("next", "today", "tomorrow", "schedule", "send", "follow up", "plan").any { it in lower }
    val lengthBonus = if (response.trim().length >= 90) 8 else if (response.trim().length >= 50) 4 else 0
    val score = (54 + lengthBonus + if (hasContext) 10 else 0 + if (hasBoundary) 10 else 0 + if (hasEmpathy) 8 else 0 + if (hasNextStep) 10 else 0).coerceAtMost(96)

    val strengths = buildList {
        if (hasContext) add("You gave the listener a reason, not just a request.")
        if (hasEmpathy) add("Your tone leaves room for the other person.")
        if (hasBoundary) add("You made the need or request visible.")
        if (isEmpty()) add("You started instead of waiting for the perfect wording.")
    }.take(3)

    val upgrade = when {
        !hasContext -> "Name the specific situation before asking for the next step."
        !hasBoundary -> "Replace a vague ask with one clear request or time."
        !hasNextStep -> "End with a small next step so the conversation can move."
        else -> "Trim one sentence and let your strongest point land earlier."
    }
    val nextCue = when (scenario.id) {
        "boundary" -> "Try: ‘I can help during the day. Could we send these by 6 PM instead?’"
        "interview" -> "Try connecting one past action to why this opportunity is timely now."
        "feedback" -> "Keep the behaviour, impact, and next agreement in that order."
        else -> "Try adding one concrete detail that shows what you already attempted."
    }
    val headline = when {
        score >= 82 -> "That lands with clarity."
        score >= 68 -> "A strong first draft."
        else -> "You have the honest starting point."
    }

    return Feedback(score, headline, strengths, upgrade, nextCue)
}
