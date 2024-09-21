<!-- Adapted from: https://www.writethedocs.org/guide/writing/beginners-guide-to-docs/ -->

# NetDoc

## User Guide

_NetDoc_ adds support for documentation from nets. 

### Features

- Create nets of type X in the Renew Editor
- Use custom tools from the new UI Element
- Simulate nets using the XY-Formalism

### Example Usage

1. Open a net of type X in the graphical editor.
2. Select the new Formalism via Simulation -> Formalism -> SampleFormalism.
3. Start the simulation. It will now run with SampleFormalism.

### Provided Commands

<!-- Use the classes implementing CLCommand. The 'help' output of the command can be a useful description -->

- `AbstractCommand` - Initiates a Simulation of type XY in the simulator
- `DelegationCommand` - Terminates a Simulation of type XY in the simulator

### Installation

Install _SamplePlugin_ by putting the JAR file inside the `dist/plugins` directory.

It will be automatically loaded at startup of Renew - see below for more configuration options.

You can also use the provided startup script by running

    sh Renew/SamplePlugin/src/main/resources/startup.sh

### Dependencies

This plugin depends on the following plugins and external libraries:

<!-- must match the `module-info.java` -->

- `Loader`: (mandated)
- `Gui`: Add a new set of drawing tools
- `Formalism`: Add a new Formalism option
- `FormalismGui`: Display a menu option to select the new Formalism

### Configuration

During the initialization of SamplePlugin, the following properties are read from the `renew.properties` file in the Renew root directory.

**NAME**|**TYPE**|**DESCRIPTION**|**DEFAULT**
:-----|:-----:|:-----|:-----
`de.renew.sampleplugin.workspace`|String|Specifies the path to your workspace, i.e.: `/path/to/your/desired/dir`.|`/Renew`
`de.renew.sampleplugin.showToolsAtStartup`|boolean|Load the drawing tools at startup.| true

## Developer Guide

### Core Classes

- `SamplePluginStartup`: Implements the `IPlugin` interface to manage the startup and shutdown.
- `XYFormalism`: Contains most logic of the XY formalism.
- `XYFormalismTools`: Sets up and adds a palette of tools to create XY drawings.

### How to Extend

_SamplePlugin_ exposes one Interface, `XYFormalismTools`. You can implement this interface to add additional tools to the Gui.

### Additional Information

<!-- Common issues, explanations for inconsistencies, etc. -->

- This plugin's name defined inside of the `plugin.cfg` file does not match the module name defined in the `module-info.java` file for the following reason: XYZ
- Other plugins might override one or more of the user properties resulting in different files loading at runtime.

<!-- Contribution Info -->

<!-- License -->
