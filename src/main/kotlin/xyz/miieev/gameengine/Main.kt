package xyz.miieev.gameengine

import org.lwjgl.BufferUtils
import org.lwjgl.glfw.GLFW.*
import org.lwjgl.opengl.ARBVertexArrayObject.glBindVertexArray
import org.lwjgl.opengl.ARBVertexArrayObject.glGenVertexArrays
import org.lwjgl.opengl.GL30.*
import kotlin.system.exitProcess

const val VERTEX_SHADER = """
    #version 330 core
    layout (location = 0) in vec2 aPos;
    void main() {
        gl_Position = vec4(aPos, 0.0, 1.0);
    }
"""

const val FRAGMENT_SHADER = """
    #version 330 core
    out vec4 FragColor;
    uniform vec4 uColor;
    void main() {
        FragColor = uColor;
    }
"""

fun main() {
    if (!glfwInit()) {
        println("Ошибка инициализации GLFW")
        exitProcess(-1)
    }

    val window = Display("Test", 600, 600)

    val vao = glGenVertexArrays()
    val vbo = glGenBuffers()

    glBindVertexArray(vao)
    glBindBuffer(GL_ARRAY_BUFFER, vbo)

    glVertexAttribPointer(0, 2, GL_FLOAT, false, 2 * 4, 0)
    glEnableVertexAttribArray(0)

    val vertexShader = glCreateShader(GL_VERTEX_SHADER).also {
        glShaderSource(it, VERTEX_SHADER)
        glCompileShader(it)
    }
    val fragmentShader = glCreateShader(GL_FRAGMENT_SHADER).also {
        glShaderSource(it, FRAGMENT_SHADER)
        glCompileShader(it)
    }

    val shaderProgram = glCreateProgram().apply {
        glAttachShader(this, vertexShader)
        glAttachShader(this, fragmentShader)
        glLinkProgram(this)
    }

    glDeleteShader(vertexShader)
    glDeleteShader(fragmentShader)

    var color = Color(255, 255, 210, 255)
    var currentTick = 0
    val colorUniform = glGetUniformLocation(shaderProgram, "uColor")

    while (!glfwWindowShouldClose(window.id)) {
        glfwPollEvents()
        glClear(GL_COLOR_BUFFER_BIT)

        println(currentTick)

        if (currentTick % 50 == 0) {
            color = color.darken(5f)
        }

        glUseProgram(shaderProgram)

        val vertices = floatArrayOf(
            -0.5f,  0.5f,
            0.5f,  0.5f,
            -0.5f, -0.5f,
        )

        val buffer = BufferUtils.createFloatBuffer(vertices.size).put(vertices).flip()
        glBufferData(GL_ARRAY_BUFFER, buffer, GL_STATIC_DRAW)

        // Используем преобразованный цвет из объекта Color
        val glColor = color.toOpenGL()
        glUniform4f(colorUniform, glColor[0], glColor[1], glColor[2], glColor[3])

        glDrawArrays(GL_TRIANGLES, 0, vertices.size / 2)

        glfwSwapBuffers(window.id)

        currentTick++
    }

    glDeleteVertexArrays(vao)
    glDeleteBuffers(vbo)
    glDeleteProgram(shaderProgram)
    glfwDestroyWindow(window.id)
    glfwTerminate()
}
