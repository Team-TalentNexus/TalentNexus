-- 求职者用户表 (job_seekers)
CREATE TABLE job_seekers (
                             id SERIAL PRIMARY KEY,
                             username VARCHAR(50) NOT NULL UNIQUE,
                             password VARCHAR(255) NOT NULL,
                             email VARCHAR(100) NOT NULL UNIQUE,
                             phone VARCHAR(20),
                             full_name VARCHAR(100) NOT NULL,
                             gender VARCHAR(10),
                             birth_date DATE,
                             address VARCHAR(255),
                             created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                             updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 简历表 (resumes)
CREATE TABLE resumes (
                         id SERIAL PRIMARY KEY,
                         job_seeker_id INT NOT NULL REFERENCES job_seekers(id) ON DELETE CASCADE,
                         title VARCHAR(100) NOT NULL,
                         summary TEXT,
                         created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                         updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 项目经历表 (project_experiences)
CREATE TABLE project_experiences (
                                     id SERIAL PRIMARY KEY,
                                     resume_id INT NOT NULL REFERENCES resumes(id) ON DELETE CASCADE,
                                     project_name VARCHAR(100) NOT NULL,
                                     description TEXT,
                                     start_date DATE,
                                     end_date DATE,
                                     role VARCHAR(100),
                                     achievements TEXT
);

-- 工作经历表 (work_experiences)
CREATE TABLE work_experiences (
                                  id SERIAL PRIMARY KEY,
                                  resume_id INT NOT NULL REFERENCES resumes(id) ON DELETE CASCADE,
                                  company_name VARCHAR(100) NOT NULL,
                                  position VARCHAR(100) NOT NULL,
                                  start_date DATE NOT NULL,
                                  end_date DATE,
                                  description TEXT,
                                  achievements TEXT
);

-- 企业表 (companies)
CREATE TABLE companies (
                           id SERIAL PRIMARY KEY,
                           username VARCHAR(50) NOT NULL UNIQUE,
                           password VARCHAR(255) NOT NULL,
                           company_name VARCHAR(100) NOT NULL UNIQUE,
                           email VARCHAR(100) NOT NULL UNIQUE,
                           phone_number VARCHAR(15),
                           website VARCHAR(100),
                           address VARCHAR(255),
                           description TEXT,
                           created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                           updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- 职位表 (job_positions)
CREATE TABLE job_positions (
                               id SERIAL PRIMARY KEY,
                               company_id INT NOT NULL REFERENCES companies(id) ON DELETE CASCADE,
                               title VARCHAR(100) NOT NULL,
                               description TEXT NOT NULL,
                               location VARCHAR(100),
                               employment_type VARCHAR(50) NOT NULL,
                               salary_range VARCHAR(50),
                               active BOOLEAN NOT NULL DEFAULT TRUE,
                               created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                               updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- 求职申请表 (job_applications)
CREATE TABLE job_applications (
                                  id SERIAL PRIMARY KEY,
                                  job_seeker_id INT NOT NULL REFERENCES job_seekers(id) ON DELETE CASCADE,
                                  job_position_id INT NOT NULL REFERENCES job_positions(id) ON DELETE CASCADE,
                                  resume_id INT REFERENCES resumes(id) ON DELETE SET NULL,
                                  application_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                  status VARCHAR(50) NOT NULL,
                                  feedback TEXT
);
