package moe.taiho.course_selection.policies

import moe.taiho.course_selection.actors.CommonMessage.Reason
import moe.taiho.course_selection.{Judge, KryoSerializable}

object StudentPolicy {
    sealed trait Command extends KryoSerializable
}

class StudentPolicy(id: Int) {
    import StudentPolicy._

    val judge = new Judge(id)

    def validateTake(course: Int): Option[Reason] = {
        val res = judge.registerCheck(course)
        if (res.result) None else Some(res.reason)
    }

    def validateQuit(course: Int): Option[Reason] = {
        val res = judge.dropCheck(course)
        if (res.result) None else Some(res.reason)
    }

    def preTake(course: Int): Unit = judge.courseTable.addCourse(course)
    def take(course: Int): Unit = {}
    def failedTake(course: Int): Unit = judge.courseTable.dropCourse(course)
    def preDrop(course: Int): Unit = judge.courseTable.dropCourse(course)
    def drop(course: Int): Unit = {}
    def setPolicy(cmd: Command): Unit = {}
}
