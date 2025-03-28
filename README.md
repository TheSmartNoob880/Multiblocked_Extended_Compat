# Multiblocked Extended Compatability Mod

An addon for adding a few extra mod compatabilities to Multiblocked on 1.18
So far, this includes: 
Ars Nouveau (Baileyholl2)
Elemental Craft (Sirttas)

///////////////////KubeJS Compatibility///////////////////////

This mod works with Multiblocked's native KubeJS integration.
Examples:

Ars Nouveau:
Input Source
.inputANSource(int: sourceAmount, *optional* String: slotName)
Output Source
.outputANSource(int: sourceAmount, *optional* String: slotName)

Elemental Craft:
Valid Strings for elementName: 'earth', 'air', 'fire', 'water'
Input Element
.inputECElement(String: elementName, int: elementAmount, *optional* String: slotName)
Output Element
.outputECElement(String: elementName, int: elementAmount, *optional* String: slotName)

EX: 
//Ars Input Example
event.recipes.multiblocked.multiblock('exampleMultiblockRecipemapName')
.inputANSource(3000)
.outputItem('minecraft:dirt')
.duration(100)

//Ars Output Example
event.recipes.multiblocked.multiblock('exampleMultiblockRecipemapName')
.inputItem('minecraft:cobblestone')
.outputANSource(400)
.duration(100)

//Elemental Craft Input Example
event.recipes.multiblocked.multiblock('exampleMultiblockRecipemapName')
.inputECElement('earth', 25000, 'earth_container')
.inputECElement('air', 25000)
.inputECElement('fire', 25000)
.inputECElement('water', 25000)
.outputItem('minecraft:diamond')
.duration(300)

//Elemental Craft Output Example
event.recipes.multiblocked.multiblock('exampleMultiblockRecipemapName')
.inputItem('minecraft:stone')
.outputECElement('fire', 1234)
.duration(100)

#   M u l t i b l o c k e d - E x t e n d e d - C o m p a t - 1 _ 1 8 
 
 
