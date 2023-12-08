import React, { useEffect, useState } from "react";
import { useParams, useNavigate } from "react-router-dom";
import {
  Button,
  TextField,
  Typography,
  Container,
  Grid,
  Paper,
} from "@mui/material";

const CourseDetails = () => {
  const [course, setCourse] = useState({});
  const { courseId } = useParams();
  const navigate = useNavigate();
  const [editMode, setEditMode] = useState(false);

  const buttonStyle = {
    backgroundColor: "#007bff",
    color: "#fff",
    padding: "10px 20px",
    border: "none",
    borderRadius: "5px",
    cursor: "pointer",
    fontWeight: "bold",
    transition: "all 0.2s ease-in-out",
    marginLeft: "10px", // Add margin-left to create horizontal space
  };

  const commonButtonStyle = {
    padding: "12px 24px",
    borderRadius: "5px",
    fontWeight: "bold",
    transition: "all 0.2s ease-in-out",
    marginLeft: "10px",
  };

  const primaryButtonStyle = {
    ...commonButtonStyle,
    backgroundColor: "#d32f2f",
    color: "#ffffff",
  };

  const secondaryButtonStyle = {
    ...commonButtonStyle,
    backgroundColor: "#cccccc",
    color: "#000000",
  };

  const headerStyle = {
    textAlign: "center",
    marginBottom: "20px",
    color: "#d32f2f"
  };

  const paperStyle = {
    padding: "20px",
    marginBottom: "20px",
    marginTop: "30px",
  };

  const textFieldStyle = {
    width: "100%",
    marginBottom: "20px",
  };

  useEffect(() => {
    fetch(
      `https://pathshala-api-8e4271465a87.herokuapp.com/pathshala/courses/${courseId}`,
      {
        headers: {
          "Content-Type": "application/json",
          "authorization-token": localStorage.getItem("token"),
          "userId": localStorage.getItem("userId"),
          "userType": localStorage.getItem("userType"),
        },
      }
    )
      .then((res) => res.json())
      .then((data) => {
        console.log("API response:", data);
        setCourse(data); // Update the 'course' state variable with the data
      })
      .catch((error) => console.error("API error:", error));
  }, [courseId]);

  const handleEditCourse = () => {
    setEditMode(!editMode); // Toggle edit mode

  };

  const handleSaveChanges = () => {
    if (editMode) {
      // Send updated course information to backend using API call
      fetch(
        `https://pathshala-api-8e4271465a87.herokuapp.com/pathshala/courses`,
        {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
            "authorization-token": localStorage.getItem("token"),
            "userId": localStorage.getItem("userId"),
            "userType": localStorage.getItem("userType"),
          },
          body: JSON.stringify(course), // Send the entire 'course' object as the request body
        }
      )
        .then((res) => res.json())
        .then((data) => {
          console.log("API response:", data);
          setCourse(data); // Update the 'course' state variable with the updated data
          setEditMode(false); // Reset edit mode

        })
        .catch((error) => console.error("API error:", error));
    }
  };

  const handleDescriptionChange = (event) => {
    const updatedCourse = { ...course, description: event.target.value };
    setCourse(updatedCourse);

  };

  const handleSyllabusChange = (event) => {
    const updatedCourse = { ...course, syllabus: event.target.value };
    setCourse(updatedCourse);

  };

  const handleGoBack = () => {
    navigate("/instructor"); // Navigate back to InstructorDashboard.js
  };

  async function handleEnrolledStudents(course) {
    const response = await fetch(
      `https://pathshala-api-8e4271465a87.herokuapp.com/pathshala/courses/${courseId}`,
      {
        headers: {
          "Content-Type": "application/json",
          "authorization-token": localStorage.getItem("token"),
          "userId": localStorage.getItem("userId"),
          "userType": localStorage.getItem("userType"),
        },
      }
    );

    if (response.ok) {
      const fullCourseData = await response.json();
      navigate(`/instructor/courseDetails/enrolledStudents/${fullCourseData.id}`, {
        state: { courseId: fullCourseData.id },
      });
    } else {
      // Handle error scenario
      console.error("Failed to fetch course details:", response.status);
    }
  }

  if (!course) {
    return <div>Loading...</div>; // You may want to add a loading indicator
  }


  const handleFileUpload = async (e) => {
    console.log(e.target.files, 'here')
    const formData = new FormData();
    formData.append('file', e.target.files[0]);
    const response = await fetch(
      `https://pathshala-api-8e4271465a87.herokuapp.com/pathshala/file/upload?courseId=${course.id}`,
      {
        method: "POST",
        headers: {
          "authorization-token": localStorage.getItem("token"),
          "userId": localStorage.getItem("userId"),
          "userType": localStorage.getItem("userType"),
        },
        body: formData
      }
    );
    const text = await response.text();
    setCourse({ ...course, filePath: text })

  }

  const handleFileDownload = () => {
    if (!course.filePath) {
      console.error("File path is null. Cannot download.");
      return;
    }
  
    const downloadUrl = `https://pathshala-api-8e4271465a87.herokuapp.com/pathshala/file/download?path=${course.filePath}`;
  
    fetch(downloadUrl, {
      method: "GET",
      headers: {
        "authorization-token": localStorage.getItem("token"),
        "userId": localStorage.getItem("userId"),
        "userType": localStorage.getItem("userType"),
      },
    })
      .then((res) => {
        if (!res.ok) {
          throw new Error(`HTTP error! Status: ${res.status}`);
        }
        return res.blob();
      })
      .then((data) => {
        const file = window.URL.createObjectURL(data);
        window.open(file);
      })
      .catch((error) => console.error("API error:", error));
  }

  return (
    <Container>
      <Paper elevation={3} style={paperStyle}>
        <Typography variant="h4" style={headerStyle}>
          {course.name} ({course.courseCode})
        </Typography>

        <Grid container spacing={3}>
          <Grid item xs={12} sm={6}>
            <span>Course description</span>
            <TextField
              id="description"
              variant="outlined"
              style={textFieldStyle}
              value={course.description}
              disabled={!editMode}
              onChange={handleDescriptionChange}
              multiline
            />
          </Grid>
          <Grid item xs={12} sm={6}>
          <span>Course Syllabus</span>
            <TextField
              id="syllabus"
              variant="outlined"
              style={textFieldStyle}
              value={course.syllabus}
              disabled={!editMode}
              onChange={handleSyllabusChange}
              multiline
            />
          </Grid>
        </Grid>

        <Button
          variant="contained"
          component="label"
          style={primaryButtonStyle}
        >
          Upload Study Material
          <input type="file" hidden onChange={(e) => handleFileUpload(e)} />
        </Button>

        {course.filePath ? ( // Conditionally render the download button
          <Button onClick={handleFileDownload} style={primaryButtonStyle}>
            Download Study Material
          </Button>
        ) : (
          // Render a disabled button if filePath is null
          <Button style={{ ...primaryButtonStyle, pointerEvents: "none" }} disabled>
            Download Study Material
          </Button>
        )}
      </Paper>

      <Grid container justifyContent="center" spacing={2}>
        <Grid item>
          <Button
            style={editMode ? secondaryButtonStyle : primaryButtonStyle}
            onClick={handleEditCourse}
          >
            {editMode ? "Cancel Edit" : "Edit Course"}
          </Button>
        </Grid>
        <Grid item>
          <Button
            style={!editMode ? secondaryButtonStyle : primaryButtonStyle}
            onClick={handleSaveChanges}
            disabled={!editMode}
          >
            Publish Course
          </Button>
        </Grid>
        <Grid item>
          <Button style={primaryButtonStyle} onClick={handleEnrolledStudents}>
            Enrolled Students
          </Button>
        </Grid>
        <Grid item>
          <Button style={primaryButtonStyle} onClick={handleGoBack}>
            Go Back To Dashboard
          </Button>
        </Grid>
      </Grid>
    </Container>
  );
};

export default CourseDetails;
