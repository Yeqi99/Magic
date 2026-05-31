param(
    [string]$Prefix = ""
)

$ErrorActionPreference = "Stop"

$ScriptDir = Split-Path -Parent $MyInvocation.MyCommand.Path
$ProjectDir = Resolve-Path (Join-Path $ScriptDir "..")

if ([string]::IsNullOrWhiteSpace($Prefix)) {
    if ([string]::IsNullOrWhiteSpace($env:MAGIC_HOME)) {
        $InstallHome = Join-Path $HOME ".magic"
    } else {
        $InstallHome = $env:MAGIC_HOME
    }
} else {
    $InstallHome = $Prefix
}

$TargetDir = Join-Path $ProjectDir "target"
$Jar = $null
if (Test-Path $TargetDir) {
    $Jar = Get-ChildItem -LiteralPath $TargetDir -Filter "Magic*.jar" |
        Where-Object { $_.Name -notlike "original-*" } |
        Sort-Object Name |
        Select-Object -Last 1
}

if ($null -eq $Jar) {
    $Mvn = Get-Command mvn -ErrorAction SilentlyContinue
    if ($null -eq $Mvn) {
        Write-Error "No built jar found and mvn is not available. Install Maven, run 'mvn package', then run this script again."
    }
    Push-Location $ProjectDir
    try {
        mvn -DskipTests package
    } finally {
        Pop-Location
    }
    $Jar = Get-ChildItem -LiteralPath $TargetDir -Filter "Magic*.jar" |
        Where-Object { $_.Name -notlike "original-*" } |
        Sort-Object Name |
        Select-Object -Last 1
}

if ($null -eq $Jar) {
    Write-Error "Could not find Magic jar in target/."
}

$BinDir = Join-Path $InstallHome "bin"
$LibDir = Join-Path $InstallHome "lib"
$DefaultSpellsDir = Join-Path $InstallHome "envs\default\spells"
New-Item -ItemType Directory -Force -Path $BinDir, $LibDir, $DefaultSpellsDir | Out-Null

$InstalledJar = Join-Path $LibDir "magic.jar"
Copy-Item -LiteralPath $Jar.FullName -Destination $InstalledJar -Force

$CmdPath = Join-Path $BinDir "magic.cmd"
@"
@echo off
java -jar "%~dp0..\lib\magic.jar" %*
"@ | Set-Content -LiteralPath $CmdPath -Encoding ASCII

$Ps1Path = Join-Path $BinDir "magic.ps1"
@"
& java -jar "`$PSScriptRoot\..\lib\magic.jar" @args
"@ | Set-Content -LiteralPath $Ps1Path -Encoding UTF8

Write-Host "Magic installed to $InstallHome"
Write-Host "Add this directory to PATH if it is not already there:"
Write-Host "  $BinDir"
Write-Host "Try:"
Write-Host "  magic repl"
