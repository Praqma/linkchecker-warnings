import hudson.plugins.analysis.util.model.Priority
import hudson.plugins.warnings.parser.Warning

def obj = [
        url          : matcher.group(1),
        parentUrl    : matcher.group(2),
        baseRef      : matcher.group(3),
        result       : matcher.group(4),
        warningString: matcher.group(5),
        infoString   : matcher.group(6),
        valid        : matcher.group(7),
        fullUrl      : matcher.group(8),
        line         : matcher.group(9),
        column       : matcher.group(10),
        name         : matcher.group(11),
        dlTime       : matcher.group(12),
        dlSize       : matcher.group(13),
        checkTime    : matcher.group(14),
        cached       : matcher.group(15),
        level        : matcher.group(16),
        modified     : matcher.group(17)
]

String jekyllTarget = "_site"
String defaultIndex = "index.html"
String source = obj.parentUrl
if (source ==~ /https?:\/\/[^\s\.].[^\s\/]*$/)
    source = $ { source } $ { "/" }
if (source ==~ /.*\/$/)
    source = $ { source } $ { defaultIndex }
source = source.replaceAll(/https?:\/\/[^\s\.].[^\s\/]*/, jekyllTarget)

def line = obj.line.toInteger()
def type = obj.level
def category = obj.result

message = "$obj.fullUrl: ($obj.result) $obj.warningString - $obj.infoString"

def priority = Priority.NORMAL
if (obj.valid.equalsIgnoreCase("true"))
    priority = Priority.LOW
if (obj.valid.equalsIgnoreCase("false"))
    priority = Priority.HIGH


return new Warning(source, line, type, category, message, priority)

