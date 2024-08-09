// App Scripts
// Event form - date interval validation
// TODO > or <, not >= or <=
let startTime = document.querySelector("#startTime")
let endTime = document.querySelector("#endTime")
const rangeMsg = "Start can not be earlier than end."
const checkRange = () => {
    if (startTime.value && endTime.value) {
        if (startTime.value > endTime.value) {
            console.log(rangeMsg)
        }
    }
}

const printValues = () => {
    console.log("START ", startTime.value)
    console.log("END ", endTime.value)
}

const setEndTimeMin = () => {
    endTime.setAttribute("min", startTime.value)
    console.log("Start: ", startTime.value)
    console.log("End MIN: ", startTime.value)
    checkRange()
    printValues()
}
const setStartTimeMax = () => {
    startTime.setAttribute("max", endTime.value)
    console.log("Start MAX: ", endTime.value)
    checkRange()
    printValues()
}
startTime.addEventListener("input", setEndTimeMin)
endTime.addEventListener("input", setStartTimeMax)