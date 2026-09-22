package com.jev.probe.core.kb

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Test

class ChatContextTest {

    @Test
    fun `contact relationship overrides the default relationship`() {
        val context = contextWithRelationship("同事，带我做项目的组长")

        assertEquals("同事，带我做项目的组长", context.effectiveRelationship("对方是我的伴侣"))
    }

    @Test
    fun `blank contact relationship falls back to the default relationship`() {
        val context = contextWithRelationship("  ")

        assertEquals("对方是我的伴侣", context.effectiveRelationship("对方是我的伴侣"))
    }

    @Test
    fun `missing contact falls back to the default relationship`() {
        val context = ChatContext(contact = null, history = emptyList(), notes = emptyList())

        assertEquals("对方是我的伴侣", context.effectiveRelationship("对方是我的伴侣"))
    }

    @Test
    fun `background does not repeat the contact relationship`() {
        val context = ChatContext(
            contact = Contact(
                id = "contact-1",
                name = "小王",
                relationship = "同事",
                notes = "负责设计评审"
            ),
            history = emptyList(),
            notes = emptyList()
        )

        val background = context.background()

        assertFalse(background.contains("关系："))
        assertEquals("关于小王：负责设计评审", background)
    }

    private fun contextWithRelationship(relationship: String) = ChatContext(
        contact = Contact(
            id = "contact-1",
            name = "小王",
            relationship = relationship
        ),
        history = emptyList(),
        notes = emptyList()
    )
}
