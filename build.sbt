name := "sbt-scoverage"

import sbt.ScriptedPlugin.autoImport.scriptedLaunchOpts

def scoverageVersion = "1.4.9+9-a154d161+20210925-1028-SNAPSHOT"

inThisBuild(
  List(
    organization := "org.scoverage",
    homepage := Some(url("http://scoverage.org/")),
    developers := List(
      Developer(
        "sksamuel",
        "Stephen Samuel",
        "sam@sksamuel.com",
        url("https://github.com/sksamuel")
      ),
      Developer(
        "gslowikowski",
        "Grzegorz Slowikowski",
        "gslowikowski@gmail.com",
        url("https://github.com/gslowikowski")
      )
    ),
    licenses := Seq(
      "Apache-2.0" -> url("http://www.apache.org/license/LICENSE-2.0")
    ),
    scalaVersion := "2.12.14"
  )
)

lazy val root = Project("sbt-scoverage", file("."))
  .enablePlugins(SbtPlugin, BuildInfoPlugin)
  .settings(
    libraryDependencies ++= Seq(
      "org.scoverage" %% "scalac-scoverage-plugin" % scoverageVersion cross (CrossVersion.full)
    ),
    buildInfoKeys := Seq[BuildInfoKey]("scoverageVersion" -> scoverageVersion),
    buildInfoPackage := "scoverage",
    Test / fork := false,
    Test / publishArtifact := false,
    Test / parallelExecution := false,
    scalacOptions := Seq(
      "-unchecked",
      "-deprecation",
      "-feature",
      "-encoding",
      "utf8"
    ),
    resolvers ++= {
      if (isSnapshot.value) Seq(Resolver.sonatypeRepo("snapshots")) else Nil
    },
    scriptedLaunchOpts ++= Seq(
      "-Xmx1024M",
      "-Dplugin.version=" + version.value
    )
  )
