/*
 *
 *  * Copyright © 2026 Manifestors Open Source
 *  * License: GPL-3.0
 *  *
 *  * All code in this project is the property of the Manifestors Open Source team
 *  * and its contributors. If you use this code in any project, please provide proper attribution
 *  * and release your project under the GPL-3.0 license as well.
 *  *
 *  * For more details, see: https://www.gnu.org/licenses/gpl-3.0.en.html
 *
 */

package com.manifestors.shinrai.client.event.events.rendering

import com.manifestors.shinrai.client.event.ConstantEvent
import net.minecraft.client.gui.DrawContext

class Rendering2DEvent(
    val context: DrawContext,
    val width: Int,
    val height: Int
) : ConstantEvent()
