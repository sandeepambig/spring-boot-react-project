import React, { useState } from 'react'

const AddStudent = () => {

    const [student,setStudent] = useState({
                                            'firstName':'',
                                            'lastName':'',
                                            'email':'',
                                            'department':''});

    const [firstName,lastName,email,department] = student;


    const handleInputChange = (e)=>{
        setStudent({...student,[e.target.name]:e.target.value})
    }
  return (
    <div className='col-sm-8 py-2 px-5'>
        <form>
            <div className='input-group mb-5'>
                <label className='input-group-text' htmlFor='firstName'>First Name</label>
                <input className='form-control col-sm-6' 
                       type='text' 
                       name='firstName' 
                       id='firstName' 
                       required value={firstName}
                       onChange={(e)=>handleInputChange}/>
            </div>
            <div className='input-group mb-5'>
                <label className='input-group-text' htmlFor='lastName'>Last Name</label>
                <input className='form-control col-sm-6' 
                       type='text' 
                       name='lastName' 
                       id='lastName' 
                       required value={lastName}
                       onChange={(e)=>handleInputChange}/>
            </div>
            <div className='input-group mb-5'>
                <label className='input-group-text' htmlFor='email'>Email</label>
                <input className='form-control col-sm-6' 
                       type='email' 
                       name='email' 
                       id='email' 
                       required value={email}
                       onChange={(e)=>handleInputChange}/>
            </div>
            <div className='input-group mb-5'>
                <label className='input-group-text' htmlFor='department'>Department</label>
                <input className='form-control col-sm-6' 
                       type='text' 
                       name='department' 
                       id='department' 
                       required value={department}
                       onChange={(e)=>handleInputChange}/>
            </div>
        </form>
    </div>
  )
}

export default AddStudent