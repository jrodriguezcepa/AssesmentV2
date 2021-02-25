/*********************************
The actual script file is inside the
slidemenu.js file - remember to link 
that to your HTML file.
**********************************/

//Creating the menu object -- You can call it whatever you want - just remember to
//have the same name as the argument.
CEPAMenu = new createSlideMenu("CEPAMenu")

//Variables to set:
CEPAMenu.menuy = 90 //The top placement of the menu.
CEPAMenu.menux = 0 //The left placement of the menu
CEPAMenu.useImages = 1 //Are you using images or not?
CEPAMenu.pxspeed = 20 //The pixel speed of the animation
CEPAMenu.timspeed = 25 //The timer speed of the animation
CEPAMenu.inset = 0 //How much the selected items should pop to the left
CEPAMenu.arrow = 0 //Set this to className that has font-family:webdings
										//if you want to use the arrow feature. Note:
										//This ONLY works on DOM capable browsers, and with
										//useImages set to 0 - It's basically just a test I did.
										//I hope to improve it later on.

//Needed dummy classes - leave in the stylesheet!
CEPAMenu.bgClass =	"CEPAMenuBG"
CEPAMenu.txtClass = "CEPAMenuText"

/*******************************************************************************
Level properties - ALL properties have to be specified in level 0
This works the same way as the CM4 script (if you have used it)

The level[0] values will be the default value. Any value not specified
in higher levels will be inherited from level[0]. If anything
is spesified in level[1], but not in level[2], level[2] (sub2 menus)
will get the property value from level[1] and so on.

The availble values to control the menu by level are:

left           - The left placement of all items in the current level ( px value )
width          - The width of all items in the current level  ( px value )
height         - The height of all items in the current level  ( px value )
between        - The number of pixels between each item in  the current level ( px value)
className      - A name of a class specified in the stylesheet to control the
	               look of all items in this level. 
	               NOTE: The class MUST be in a stylesheet, and it most have position:absolute.
classNameA     - A name of a class specified in the stylesheet that will control the 
 								 Look of the TEXT for all items in this level. (you can also specify 
								 a hover class for this className to get a mouseover effect on the 
								 text.
regImage 			 - If you choose to use image feature of the script you have to
                 spesify the default image here.
roundImg       - This is the image that will used when a item is selected.
roundImg2      - This is the image used for sub-levels on the last item in a list.
								 (that's how the last items in this example are rounded on the sub-levels)
subImg         - The image used when the item has sub-items. Only in use for sub-levels
subRound       - Same as roundImg2 - only for items that have sub-items.

To understand the image setup see the images supplied with this script.
NOTE: Always specify the full (relative) path to the images!

The slideMenu_makeLevel function provides a shortcut to making levels.
If you are more comfortable with setting the properties by name you can
also set them like this:

CEPAMenu.level[0] = new Array()
CEPAMenu.level[0].left = 0
CEPAMenu.level[0].width = 138
..... and so on.

NOTE: In level 0 below I have included the names of the variables just for
readability - feel free to remove left = , width = , height = et cetera...
********************************************************************************/
CEPAMenu.level[0] = new slideMenu_makeLevel(
	left = 0,
	width = 150,
	height = 35,
	between = 0,
	className = "clCEPAMenu",
	classNameA = "clA0",
	regImage = "./imgs/level0_regular.gif",
	roundImg = "./imgs/level0_round.gif",
	roundImg2 = "",
	subImg = "",
	subRound= "")
	
CEPAMenu.level[1] = new slideMenu_makeLevel(0,150,35,0,"clCEPAMenu","clA1","./imgs/level1_regular.gif","./imgs/level1_round2.gif","./imgs/level1_round.gif","./imgs/level1_sub.gif", "./imgs/level1_sub_round.gif")
CEPAMenu.level[2] = new slideMenu_makeLevel(0,150,35,0,"clCEPAMenu","clA2","./imgs/level2_regular.gif","./imgs/level2_round2.gif","./imgs/level2_round.gif", "./imgs/level2_sub.gif", "./imgs/level2_sub_round.gif")
CEPAMenu.level[3] = new slideMenu_makeLevel(0,150,35,0,"clCEPAMenu","clA3","./imgs/level3_regular.gif","./imgs/level3_round2.gif","./imgs/level3_round.gif","./imgs/level3_sub.gif","./imgs/level3_sub_round.gif")
CEPAMenu.level[4] = new slideMenu_makeLevel(0,150,35,0,"clCEPAMenu","clA4","./imgs/level4_regular.gif", "./imgs/level4_round2.gif","./imgs/level4_round.gif","./imgs/level4_sub.gif", "./imgs/level4_sub_round.gif")

//Image preload --- leave this
for(var i=0;i<CEPAMenu.level;i++){
	var l = CEPAMenu.level[i]
	new preLoadBackgrounds(l.regImage,l.roundImg,l.roundImg2,l.subImg,l.subRound)
}

/**********************************************************************
Making the menus is the same as in SlideMenu 1 only that now
you can make as many levels as you want.

NOTE: If you are converting from SlideMenu1 remember to add: theNameOfYourMenu.
in front of all menu creation calls.

The arguments to the makeMenu function are:

TYPE - top for top item, sub for sub item, sub2 for sub2 item, sub3 
       for sub3 item and so on (I've done it like that to keep it the same way as version 1)

TEXT - The link text for the item

TARGET - The target frame to open the links in. You do not have to spesify this if you
are not in a frame enviroment. (see below for more info)
------------------------------------------------------------
NOTE: I get mail about this all the time so I will try and explain more:
If you where to make a regular link that would open a link in another
frame you would probably do like this:
<a href="mylink.html" target="myOtherFrameName">Link</a>

To do the same for a slideMenu link you do like this:

CEPAMenu.makeMenu('top','My link','mylink.html','myOtherFrameName')
------------------------------------------------------------

myCEPAMenu.makeMenu('TYPE','TEXT','LINK','TARGET')
************************************************************************/
