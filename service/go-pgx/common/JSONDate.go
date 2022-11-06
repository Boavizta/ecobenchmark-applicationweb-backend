package common

//src: https://programmer.group/elegant-implementation-of-go-json-time-format.html

import (
	"fmt"
	"time"
)

const timeFormatYMDhms = "2006-01-02T15:04:05Z" // Time format used by json
type JSONDATE struct {
	time.Time // Time types used by json
}

// JSONDATE deserialization
func (self *JSONDATE) UnmarshalJSON(data []byte) (err error) {
	newTime, _ := time.ParseInLocation("\""+timeFormatYMDhms+"\"", string(data), time.Local)
	*&self.Time = newTime
	return
}

// JSONDATE serialization
func (self JSONDATE) MarshalJSON() ([]byte, error) {
	timeStr := fmt.Sprintf("\"%s\"", self.Format(timeFormatYMDhms))
	return []byte(timeStr), nil
}

//Output string
func (self JSONDATE) String() string {
	return self.Time.Format(timeFormatYMDhms)
}
