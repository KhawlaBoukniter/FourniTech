# granular-commits-fixed.ps1
# Usage: exécuter depuis la racine du repo

$AI_COMMIT_EXE = "C:\Users\boukn\AppData\Roaming\npm\aicommits.cmd"
$startDate = Get-Date "2025-10-27"
$endDate   = Get-Date "2025-11-07"

# --- Step 0: safety commit backup ---
if (-not (Test-Path ".git")) { git init }
Write-Host "Creating a backup commit..."
git add -A
git commit -m "WIP backup: full project before granular history" | Out-Null

# create working branch and reset last commit
git checkout -b hist-granular
git reset HEAD~1

# --- helper functions ---

# Génère une date aléatoire strictement entre start et end
function Get-RandomDateBetween($start, $end) {
    $startTicks = $start.Ticks
    $endTicks = $end.Ticks
    $randTicks = Get-Random -Minimum $startTicks -Maximum $endTicks
    return [DateTime]$randTicks
}

# Répartir les commits sur la plage de dates de manière proportionnelle
function Get-SequentialDate($start, $end, $idx, $total) {
    $daysTotal = ($end - $start).Days + 1
    $dayIndex = [math]::Floor($idx * $daysTotal / $total)
    $baseDate = $start.AddDays($dayIndex)
    $hour = Get-Random -Minimum 9 -Maximum 18
    $minute = Get-Random -Minimum 0 -Maximum 59
    return (Get-Date ($baseDate.ToString("yyyy-MM-dd") + " " + "{0:D2}:{1:D2}:00" -f $hour, $minute))
}

function Commit-File($file, $idx, $total) {
    if (-not (Test-Path $file)) { return $idx }

    git add -- $file
    $aiOutput = & $AI_COMMIT_EXE --generate 1 2>&1
    $candidate = ($aiOutput | Where-Object { $_ -and $_.Trim() } | Select-Object -Last 1)
    if (-not $candidate) { $candidate = "chore: update $file" }

    $commitDate = Get-SequentialDate $startDate $endDate $idx $total
    git commit -m "$candidate" --date="$($commitDate.ToString('yyyy-MM-ddTHH:mm:ss'))" | Out-Null
    Write-Host "Committed $file as [$($commitDate.ToString('yyyy-MM-dd HH:mm'))] : $candidate"

    return $idx + 1
}

# --- Step 1: commit initial files (config, root, .idea, etc.) ---
$initialFiles = @(".idea\*", "README*", "pom.xml", "build.gradle", "applicationContext.xml")
$filesToAdd = @()
foreach ($pattern in $initialFiles) {
    $expanded = Get-ChildItem -Path $pattern -Recurse -ErrorAction SilentlyContinue | Where-Object {!$_.PsIsContainer} | ForEach-Object { $_.FullName }
    foreach ($f in $expanded) { if (-not $filesToAdd.Contains($f)) { $filesToAdd += $f } }
}

$idx = 0
$totalInitial = $filesToAdd.Count
foreach ($file in $filesToAdd) {
    $idx = Commit-File $file $idx $totalInitial
}

# --- Step 2: commit tous les fichiers Java dans src/ ---
$javaFiles = Get-ChildItem -Path "src" -Recurse -Include *.java | ForEach-Object { $_.FullName }
$totalJava = $javaFiles.Count

foreach ($file in $javaFiles) {
    $idx = Commit-File $file $idx $totalJava
}

Write-Host "`nGranular history reconstruction completed!"
Write-Host "Branch: hist-granular"
Write-Host "Inspect history: git log --oneline --graph --decorate"

