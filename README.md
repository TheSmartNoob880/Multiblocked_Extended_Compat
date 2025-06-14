# Multiblocked Extended Compatibility Mod

An addon for adding a few extra mod compatibilities to Multiblocked on 1.18

So far, this includes:
- Ars Nouveau (Baileyholl2)
- ElementalCraft (Sirttas)

### KubeJS Compatibility

This mod works with Multiblocked's native KubeJS integration.


### Ars Nouveau:
Add Source as a input:
```
.inputANSource(int: sourceAmount, *optional* "String: slotName")
```
Add Source as a Output:
```
.outputANSource(int: sourceAmount, *optional* "String: slotName")
```

### ElementalCraft:
#### Valid Strings for elementName: ```earth```, ```air```, ```fire```, ```water```
Add a Element as a Input:
```
.inputECElement("String: elementName", int: elementAmount, *optional* "String: slotName")
```
Add an Element as a Output:
```
.outputECElement("String: elementName", int: elementAmount, *optional* "String: slotName")
```
## Examples:
```
//Ars Input Example
event.recipes.multiblocked.multiblock('exampleMultiblockRecipemapName')
.inputANSource(3000)
.outputItem('minecraft:dirt')
.duration(100)
```

```
//Ars Output Example
event.recipes.multiblocked.multiblock('exampleMultiblockRecipemapName')
.inputItem('minecraft:cobblestone')
.outputANSource(400)
.duration(100)
```
```
//ElementalCraft Input Example
event.recipes.multiblocked.multiblock('exampleMultiblockRecipemapName')
.inputECElement('earth', 25000, 'earth_container')
.inputECElement('air', 25000)
.inputECElement('fire', 25000)
.inputECElement('water', 25000)
.outputItem('minecraft:diamond')
.duration(300)
```
```
//ElementalCraft Output Example
event.recipes.multiblocked.multiblock('exampleMultiblockRecipemapName')
.inputItem('minecraft:stone')
.outputECElement('fire', 1234)
.duration(100)
```
#
