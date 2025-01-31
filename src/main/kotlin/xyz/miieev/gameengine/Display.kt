package xyz.miieev.gameengine

import org.lwjgl.glfw.GLFW.*
import org.lwjgl.opengl.GL
import org.lwjgl.system.MemoryUtil
import kotlin.system.exitProcess

class Display(title: String, width: Int, height: Int) {
    val id: Long = glfwCreateWindow(width, height, title, MemoryUtil.NULL, MemoryUtil.NULL)

    init {
        if (id == MemoryUtil.NULL) {
            glfwTerminate()
            exitProcess(-1)
        }
        glfwMakeContextCurrent(id)
        GL.createCapabilities()
    }

    fun show() {
        glfwShowWindow(id)
    }

    fun hide() {
        glfwHideWindow(id)
    }
}